package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    //Hiển thị tất cả người dùng
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String code = rs.getString("Code");
                String fullName = rs.getString("Fullname");
                String address = rs.getString("Address");
                String className = rs.getString("Class");
                String password = rs.getString("Password");
                String role = rs.getString("Role");

                User user = new User(code, fullName, address, className, password, role);
                userList.add(user);
            }
        } catch (SQLException e) {
            return userList; // Trả về danh sách rỗng thay vì hiển thị lỗi
        }
        return userList;
    }

    //Thêm người dùng
    public boolean addUser(User user) {
        if (userExists(user.getUserCode())) {
            return false; // Mã đã tồn tại
        }
        String query = "INSERT INTO User (Code, Fullname, Address, Class, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
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
            return false;
        }
    }

    //Cập nhật người dùng
    public boolean updateUser(User user) {
        String query = "UPDATE User SET Fullname=?, Address=?, Class=?, Password=?, Role=? WHERE Code=?";
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
            return false;
        }
    }

    //Xóa người dùng
    public boolean deleteUser(String userCode) {
        String query = "DELETE FROM User WHERE Code=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Kiểm tra sự tồn tại người dùng
    public boolean userExists(String userCode) {
        String query = "SELECT COUNT(*) FROM User WHERE Code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}