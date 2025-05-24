package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp Data Access Object để xử lý các thao tác liên quan đến người dùng trong cơ sở dữ liệu
public class UserDAO {
    private final Connection connection;

    // Hàm khởi tạo để thiết lập kết nối cơ sở dữ liệu
    public UserDAO() {
        connection = ConnectDB.getConnection();
    }

    // Lấy danh sách tất cả người dùng từ cơ sở dữ liệu
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return userList;
        }

        String query = "SELECT * FROM User";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Duyệt qua tập kết quả và tạo các đối tượng User
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
            System.err.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        }
        return userList;
    }

    // Thêm một người dùng mới vào cơ sở dữ liệu
    public boolean addUser(User user) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của dữ liệu người dùng
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) || 
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Dữ liệu người dùng không hợp lệ: mã, vai trò, tên, địa chỉ hoặc các trường đặc trưng theo vai trò.");
            return false;
        }
        // Kiểm tra xem mã người dùng đã tồn tại chưa
        if (userExists(user.getUserCode())) {
            System.err.println("Mã người dùng đã tồn tại: " + user.getUserCode());
            return false;
        }

        String query = "INSERT INTO User (Code, Fullname, Address, Class, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setString(1, user.getUserCode());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getClassName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            // Thực thi thêm và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin người dùng trong cơ sở dữ liệu
    public boolean updateUser(User user) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của dữ liệu người dùng
        if (!User.isValidCode(user.getUserCode()) || 
            !User.isValidRole(user.getRole()) ||
            !User.isValidName(user.getFullName()) ||
            !User.isValidAddress(user.getAddress()) ||
            !user.isValidForRole()) {
            System.err.println("Dữ liệu người dùng không hợp lệ: mã, vai trò, tên, địa chỉ hoặc các trường đặc trưng theo vai trò.");
            return false;
        }

        String query = "UPDATE User SET Fullname=?, Address=?, Class=?, Password=?, Role=? WHERE Code=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getClassName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setString(6, user.getUserCode());
            // Thực thi cập nhật và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật người dùng: " + e.getMessage());
            return false;
        }
    }

    // Xóa một người dùng khỏi cơ sở dữ liệu dựa trên mã người dùng
    public boolean deleteUser(String userCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của mã người dùng
        if (!User.isValidCode(userCode)) {
            System.err.println("Mã người dùng không hợp lệ.");
            return false;
        }
        // Kiểm tra xem người dùng có điểm trong User_Subject không
        String checkQuery = "SELECT COUNT(*) FROM User_Subject WHERE UserCode = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, userCode);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.err.println("Không thể xóa người dùng vì đã có điểm số liên quan.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra điểm số của người dùng: " + e.getMessage());
            return false;
        }

        String query = "DELETE FROM User WHERE Code=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            // Thực thi xóa và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra xem một người dùng có tồn tại trong cơ sở dữ liệu không
    public boolean userExists(String userCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
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
            System.err.println("Lỗi khi kiểm tra sự tồn tại của người dùng: " + e.getMessage());
        }
        return false;
    }

    // Kiểm tra xem một người dùng có phải là sinh viên không
    public boolean isStudent(String userCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
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
            System.err.println("Lỗi khi kiểm tra vai trò người dùng: " + e.getMessage());
        }
        return false;
    }
}