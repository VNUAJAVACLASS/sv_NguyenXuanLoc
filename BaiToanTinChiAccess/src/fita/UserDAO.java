package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        if (connection == null) {
            System.err.println("No database connection.");
            return userList;
        }

        String query = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("Code"),
                        rs.getString("Fullname"),
                        rs.getString("Address"),
                        rs.getString("Class"),
                        rs.getString("Password"),
                        rs.getString("Role")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return userList;
    }

    public boolean addUser(User user) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) || 
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Invalid user data: code, role, name, address, or role-specific fields.");
            return false;
        }
        if (userExists(user.getUserCode())) {
            System.err.println("User code already exists: " + user.getUserCode());
            return false;
        }

        String query = "INSERT INTO User (Code, Fullname, Address, Class, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUserCode());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getClassName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) ||
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Invalid user data: code, role, name, address, or role-specific fields.");
            return false;
        }

        String query = "UPDATE User SET Fullname=?, Address=?, Class=?, Password=?, Role=? WHERE Code=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getClassName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getUserCode());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String userCode) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!User.isValidCode(userCode)) {
            System.err.println("Invalid user code.");
            return false;
        }
        // Kiểm tra xem người dùng có điểm trong User_Subject không
        String checkQuery = "SELECT COUNT(*) FROM User_Subject WHERE UserCode = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, userCode);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.err.println("Cannot delete user with existing scores.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user scores: " + e.getMessage());
            return false;
        }

        String query = "DELETE FROM User WHERE Code=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public boolean userExists(String userCode) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }

        String query = "SELECT COUNT(*) FROM User WHERE Code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    public boolean isStudent(String userCode) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        String query = "SELECT Role FROM User WHERE Code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Role").equalsIgnoreCase("Student");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking user role: " + e.getMessage());
        }
        return false;
    }
}