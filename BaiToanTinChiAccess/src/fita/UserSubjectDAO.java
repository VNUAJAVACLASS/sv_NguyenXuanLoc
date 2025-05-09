package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSubjectDAO {
    private Connection connection;

    public UserSubjectDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    //Hiển thị
    public List<UserSubject> getAllUserSubjects() {
        List<UserSubject> list = new ArrayList<>();
        String query = "SELECT * FROM User_Subject";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String userCode = rs.getString("UserCode");
                String subjectCode = rs.getString("SubjectCode");
                double s1 = rs.getDouble("Score1");
                double s2 = rs.getDouble("Score2");
                double s3 = rs.getDouble("Score3");
                double s4 = rs.getDouble("Score4");
                double s5 = rs.getDouble("Score5");

                UserSubject us = new UserSubject(userCode, subjectCode, s1, s2, s3, s4, s5);
                list.add(us);
            }
        } catch (SQLException e) {
            System.err.println(" Lỗi khi lấy danh sách điểm: " + e.getMessage());
        }

        return list;
    }

    //Thêm
    public boolean addUserSubject(UserSubject us) {
        String query = "INSERT INTO User_Subject (UserCode, SubjectCode, Score1, Score2, Score3, Score4, Score5) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, us.getUserCode());
            stmt.setString(2, us.getSubjectCode());
            stmt.setDouble(3, us.getScore1());
            stmt.setDouble(4, us.getScore2());
            stmt.setDouble(5, us.getScore3());
            stmt.setDouble(6, us.getScore4());
            stmt.setDouble(7, us.getScore5());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println(" Lỗi khi thêm điểm: " + e.getMessage());
            return false;
        }
    }

    //Cập nhật
    public boolean updateUserSubject(UserSubject us) {
        String query = "UPDATE User_Subject SET Score1=?, Score2=?, Score3=?, Score4=?, Score5=? WHERE UserCode=? AND SubjectCode=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, us.getScore1());
            stmt.setDouble(2, us.getScore2());
            stmt.setDouble(3, us.getScore3());
            stmt.setDouble(4, us.getScore4());
            stmt.setDouble(5, us.getScore5());
            stmt.setString(6, us.getUserCode());
            stmt.setString(7, us.getSubjectCode());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println(" Lỗi khi cập nhật điểm: " + e.getMessage());
            return false;
        }
    }

    //Xóa
    public boolean deleteUserSubject(String userCode, String subjectCode) {
        String query = "DELETE FROM User_Subject WHERE UserCode=? AND SubjectCode=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            stmt.setString(2, subjectCode);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println(" Lỗi khi xóa điểm: " + e.getMessage());
            return false;
        }
    }

    public List<String> getFullScoreBoard() {
        List<String> result = new ArrayList<>();
        String sql = """
            SELECT u.UserCode, u.Fullname, s.SubjectCode, s.SubjectName,
                   us.Score1, us.Score2, us.Score3, us.Score4, us.Score5,
                   s.He1, s.He2, s.He3, s.He4, s.He5
            FROM (User AS u
            INNER JOIN User_Subject AS us ON u.UserCode = us.UserCode)
            INNER JOIN Subject AS s ON us.SubjectCode = s.SubjectCode
        """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                double averageScore = rs.getDouble("Score1") * rs.getDouble("He1") +
                                     rs.getDouble("Score2") * rs.getDouble("He2") +
                                     rs.getDouble("Score3") * rs.getDouble("He3") +
                                     rs.getDouble("Score4") * rs.getDouble("He4") +
                                     rs.getDouble("Score5") * rs.getDouble("He5");

                String line = String.format(
                    "User: %s (%s) | Môn: %s - %s | Điểm: %.1f %.1f %.1f %.1f %.1f | Trung bình: %.1f",
                    rs.getString("UserCode"),
                    rs.getString("Fullname"),
                    rs.getString("SubjectCode"),
                    rs.getString("SubjectName"),
                    rs.getDouble("Score1"),
                    rs.getDouble("Score2"),
                    rs.getDouble("Score3"),
                    rs.getDouble("Score4"),
                    rs.getDouble("Score5"),
                    averageScore
                );
                result.add(line);
            }
        } catch (SQLException e) {
            System.err.println(" Lỗi khi lấy bảng điểm: " + e.getMessage());
        }
        return result;
    }
}