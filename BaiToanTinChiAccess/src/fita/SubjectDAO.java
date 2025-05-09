package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    public SubjectDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    //Hiển thị tất cả môn học
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT * FROM Subject";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String code = rs.getString("Subjectcode");
                String name = rs.getString("Subjectname");
                int credit = rs.getInt("Credit");
                double he1 = rs.getDouble("He1");
                double he2 = rs.getDouble("He2");
                double he3 = rs.getDouble("He3");
                double he4 = rs.getDouble("He4");
                double he5 = rs.getDouble("He5");

                Subject subject = new Subject(code, name, credit, he1, he2, he3, he4, he5);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            return subjects;
        }
        return subjects;
    }

    //Thêm môn học
    public boolean addSubject(Subject subject) {
        if (subjectExists(subject.getSubjectCode())) {
            return false; // Mã môn học đã tồn tại
        }
        String query = "INSERT INTO Subject (Subjectcode, Subjectname, Credit, He1, He2, He3, He4, He5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject.getSubjectCode());
            stmt.setString(2, subject.getSubjectName());
            stmt.setInt(3, subject.getCredit());
            stmt.setDouble(4, subject.getHe1());
            stmt.setDouble(5, subject.getHe2());
            stmt.setDouble(6, subject.getHe3());
            stmt.setDouble(7, subject.getHe4());
            stmt.setDouble(8, subject.getHe5());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Cập nhật môn học
    public boolean updateSubject(Subject subject) {
        String query = "UPDATE Subject SET Subjectname=?, Credit=?, He1=?, He2=?, He3=?, He4=?, He5=? WHERE Subjectcode=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject.getSubjectName());
            stmt.setInt(2, subject.getCredit());
            stmt.setDouble(3, subject.getHe1());
            stmt.setDouble(4, subject.getHe2());
            stmt.setDouble(5, subject.getHe3());
            stmt.setDouble(6, subject.getHe4());
            stmt.setDouble(7, subject.getHe5());
            stmt.setString(8, subject.getSubjectCode());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Xóa môn học
    public boolean deleteSubject(String subjectCode) {
        String query = "DELETE FROM Subject WHERE Subjectcode=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Kiểm tra sự tồn tại của môn học
    public boolean subjectExists(String subjectCode) {
        String query = "SELECT COUNT(*) FROM Subject WHERE Subjectcode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
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