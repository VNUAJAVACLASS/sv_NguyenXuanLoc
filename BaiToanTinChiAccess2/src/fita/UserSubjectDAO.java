package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp Data Access Object để xử lý các thao tác liên quan đến điểm số của người dùng trong cơ sở dữ liệu
public class UserSubjectDAO {
    private final Connection connection;

    // Hàm khởi tạo để thiết lập kết nối cơ sở dữ liệu
    public UserSubjectDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    // Lấy danh sách tất cả các điểm số của người dùng
    public List<UserSubject> getAllUserSubjects() {
        List<UserSubject> list = new ArrayList<>();
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return list;
        }

        String query = "SELECT * FROM User_Subject";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Duyệt qua tập kết quả và tạo các đối tượng UserSubject
            while (rs.next()) {
                UserSubject us = new UserSubject(
                        rs.getString("UserCode"),
                        rs.getString("SubjectCode"),
                        rs.getDouble("Score1"),
                        rs.getDouble("Score2"),
                        rs.getDouble("Score3"),
                        rs.getDouble("Score4"),
                        rs.getDouble("Score5")
                );
                list.add(us);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách điểm số: " + e.getMessage());
        }
        return list;
    }

    // Thêm điểm số cho một người dùng và môn học
    public boolean addUserSubject(UserSubject us) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của điểm số
        if (!us.areScoresValid()) {
            System.err.println("Điểm số không hợp lệ.");
            return false;
        }
        // Kiểm tra xem người dùng đã có điểm cho môn học này chưa
        if (userSubjectExists(us.getUserCode(), us.getSubjectCode())) {
            System.err.println("Người dùng đã có điểm cho môn học này.");
            return false;
        }

        String query = "INSERT INTO User_Subject (UserCode, SubjectCode, Score1, Score2, Score3, Score4, Score5) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setString(1, us.getUserCode());
            stmt.setString(2, us.getSubjectCode());
            stmt.setDouble(3, us.getScore1());
            stmt.setDouble(4, us.getScore2());
            stmt.setDouble(5, us.getScore3());
            stmt.setDouble(6, us.getScore4());
            stmt.setDouble(7, us.getScore5());
            // Thực thi thêm và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm điểm số: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật điểm số cho một người dùng và môn học
    public boolean updateUserSubject(UserSubject us) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của điểm số
        if (!us.areScoresValid()) {
            System.err.println("Điểm số không hợp lệ.");
            return false;
        }

        String query = "UPDATE User_Subject SET Score1=?, Score2=?, Score3=?, Score4=?, Score5=? WHERE UserCode=? AND SubjectCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setDouble(1, us.getScore1());
            stmt.setDouble(2, us.getScore2());
            stmt.setDouble(3, us.getScore3());
            stmt.setDouble(4, us.getScore4());
            stmt.setDouble(5, us.getScore5());
            stmt.setString(6, us.getUserCode());
            stmt.setString(7, us.getSubjectCode());
            // Thực thi cập nhật và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật điểm số: " + e.getMessage());
            return false;
        }
    }

    // Xóa điểm số của một người dùng và môn học
    public boolean deleteUserSubject(String userCode, String subjectCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }

        String query = "DELETE FROM User_Subject WHERE UserCode=? AND SubjectCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            stmt.setString(2, subjectCode);
            // Thực thi xóa và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa điểm số: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra xem người dùng đã có điểm cho môn học này chưa
    public boolean userSubjectExists(String userCode, String subjectCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }

        String query = "SELECT COUNT(*) FROM User_Subject WHERE UserCode = ? AND SubjectCode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            stmt.setString(2, subjectCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của điểm số: " + e.getMessage());
        }
        return false;
    }

    // Lấy bảng điểm đầy đủ bao gồm thông tin người dùng, môn học và điểm số trung bình
    public List<String> getFullScoreBoard() {
        List<String> result = new ArrayList<>();
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return result;
        }

        String sql = """
                SELECT u.Code, u.Fullname, s.Subjectcode, s.SubjectName,
                       us.Score1, us.Score2, us.Score3, us.Score4, us.Score5,
                       s.He1, s.He2, s.He3, s.He4, s.He5
                FROM (User AS u
                INNER JOIN User_Subject AS us ON u.Code = us.UserCode)
                INNER JOIN Subject AS s ON us.SubjectCode = s.Subjectcode
                """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Duyệt qua tập kết quả và tính điểm trung bình
            while (rs.next()) {
                double averageScore = rs.getDouble("Score1") * rs.getDouble("He1") +
                                     rs.getDouble("Score2") * rs.getDouble("He2") +
                                     rs.getDouble("Score3") * rs.getDouble("He3") +
                                     rs.getDouble("Score4") * rs.getDouble("He4") +
                                     rs.getDouble("Score5") * rs.getDouble("He5");
                String line = String.format(
                        "User: %s (%s) | Subject: %s (%s) | Scores: %.1f, %.1f, %.1f, %.1f, %.1f | Average: %.2f",
                        rs.getString("Code"), rs.getString("Fullname"),
                        rs.getString("Subjectcode"), rs.getString("SubjectName"),
                        rs.getDouble("Score1"), rs.getDouble("Score2"), rs.getDouble("Score3"),
                        rs.getDouble("Score4"), rs.getDouble("Score5"), averageScore
                );
                result.add(line);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy bảng điểm: " + e.getMessage());
        }
        return result;
    }
}