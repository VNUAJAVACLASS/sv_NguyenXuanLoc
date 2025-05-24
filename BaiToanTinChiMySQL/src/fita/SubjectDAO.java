package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp Data Access Object để xử lý các thao tác liên quan đến môn học trong cơ sở dữ liệu
public class SubjectDAO {
    private final Connection connection;

    // Hàm khởi tạo để thiết lập kết nối cơ sở dữ liệu
    public SubjectDAO() {
        connection = ConnectDB.getConnection();
    }

    // Lấy danh sách tất cả các môn học từ cơ sở dữ liệu
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        // Kiểm tra xem kết nối cơ sở dữ liệu có sẵn không
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return subjects;
        }

        String query = "SELECT * FROM Subject";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Duyệt qua tập kết quả và tạo các đối tượng Subject
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getString("Subjectcode"),
                        rs.getString("Subjectname"),
                        rs.getInt("Credit"),
                        rs.getDouble("He1"),
                        rs.getDouble("He2"),
                        rs.getDouble("He3"),
                        rs.getDouble("He4"),
                        rs.getDouble("He5")
                );
                subjects.add(subject);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách môn học: " + e.getMessage());
        }
        return subjects;
    }

    // Thêm một môn học mới vào cơ sở dữ liệu
    public boolean addSubject(Subject subject) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của dữ liệu môn học
        if (!Subject.isValidCode(subject.getSubjectCode()) || !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Mã môn học, số tín chỉ hoặc trọng số không hợp lệ.");
            return false;
        }
        // Kiểm tra xem mã môn học đã tồn tại chưa
        if (subjectExists(subject.getSubjectCode())) {
            System.err.println("Mã môn học đã tồn tại: " + subject.getSubjectCode());
            return false;
        }

        String query = "INSERT INTO Subject (Subjectcode, Subjectname, Credit, He1, He2, He3, He4, He5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setString(1, subject.getSubjectCode());
            stmt.setString(2, subject.getSubjectName());
            stmt.setInt(3, subject.getCredit());
            stmt.setDouble(4, subject.getHe1());
            stmt.setDouble(5, subject.getHe2());
            stmt.setDouble(6, subject.getHe3());
            stmt.setDouble(7, subject.getHe4());
            stmt.setDouble(8, subject.getHe5());
            // Thực thi cập nhật và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm môn học: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin môn học trong cơ sở dữ liệu
    public boolean updateSubject(Subject subject) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của dữ liệu môn học
        if (!Subject.isValidCode(subject.getSubjectCode()) || !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Mã môn học, số tín chỉ hoặc trọng số không hợp lệ.");
            return false;
        }

        String query = "UPDATE Subject SET Subjectname=?, Credit=?, He1=?, He2=?, He3=?, He4=?, He5=? WHERE Subjectcode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Thiết lập các tham số cho câu lệnh chuẩn bị
            stmt.setString(1, subject.getSubjectName());
            stmt.setInt(2, subject.getCredit());
            stmt.setDouble(3, subject.getHe1());
            stmt.setDouble(4, subject.getHe2());
            stmt.setDouble(5, subject.getHe3());
            stmt.setDouble(6, subject.getHe4());
            stmt.setDouble(7, subject.getHe5());
            stmt.setString(8, subject.getSubjectCode());
            // Thực thi cập nhật và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật môn học: " + e.getMessage());
            return false;
        }
    }

    // Xóa một môn học khỏi cơ sở dữ liệu dựa trên mã môn học
    public boolean deleteSubject(String subjectCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }
        // Kiểm tra tính hợp lệ của mã môn học
        if (!Subject.isValidCode(subjectCode)) {
            System.err.println("Mã môn học không hợp lệ.");
            return false;
        }

        String query = "DELETE FROM Subject WHERE Subjectcode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
            // Thực thi xóa và trả về trạng thái thành công
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa môn học: " + e.getMessage());
            return false;
        }
    }

    // Kiểm tra xem một môn học có tồn tại trong cơ sở dữ liệu dựa trên mã môn học
    public boolean subjectExists(String subjectCode) {
        // Kiểm tra kết nối cơ sở dữ liệu
        if (connection == null) {
            System.err.println("Không có kết nối cơ sở dữ liệu.");
            return false;
        }

        String query = "SELECT COUNT(*) FROM Subject WHERE Subjectcode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra sự tồn tại của môn học: " + e.getMessage());
        }
        return false;
    }
}