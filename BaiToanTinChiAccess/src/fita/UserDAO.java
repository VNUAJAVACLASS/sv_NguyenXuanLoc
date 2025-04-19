package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            String dbURL = "jdbc:ucanaccess://C://Users//nguye//Documents//Database 13 4 2025.accdb";
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String code = rs.getNString("UserCode");
                String fullName = rs.getString("Fullname");
                String address = rs.getNString("Address");
                String class_ = rs.getString("Class");
                String password = rs.getNString("Password");
                String role = rs.getString("Role");

                User user = new User(code, fullName, address, class_, password, role);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Thêm người dùng
    public boolean addUser(User user) {
        String query = "INSERT INTO User (UserCode, Fullname, Address, Class, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUserCode());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getClassName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        String query = "UPDATE User SET Fullname=?, Address=?, Class=?, Password=?, Role=? WHERE UserCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getClassName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getUserCode());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa người dùng theo mã
    public boolean deleteUser(String userCode) {
        String query = "DELETE FROM User WHERE UserCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
