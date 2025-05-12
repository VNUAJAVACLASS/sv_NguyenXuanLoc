package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSubjectDAO {
    private final Connection connection;

    public UserSubjectDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    public List<UserSubject> getAllUserSubjects() {
        List<UserSubject> list = new ArrayList<>();
        if (connection == null) {
            System.err.println("No database connection.");
            return list;
        }

        String query = "SELECT * FROM User_Subject";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
            System.err.println("Error fetching user subjects: " + e.getMessage());
        }
        return list;
    }

    public boolean addUserSubject(UserSubject us) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!us.areScoresValid()) {
            System.err.println("Invalid scores provided.");
            return false;
        }
        if (userSubjectExists(us.getUserCode(), us.getSubjectCode())) {
            System.err.println("User already has scores for this subject.");
            return false;
        }

        String query = "INSERT INTO User_Subject (UserCode, SubjectCode, Score1, Score2, Score3, Score4, Score5) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, us.getUserCode());
            stmt.setString(2, us.getSubjectCode());
            stmt.setDouble(3, us.getScore1());
            stmt.setDouble(4, us.getScore2());
            stmt.setDouble(5, us.getScore3());
            stmt.setDouble(6, us.getScore4());
            stmt.setDouble(7, us.getScore5());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user subject: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUserSubject(UserSubject us) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!us.areScoresValid()) {
            System.err.println("Invalid scores provided.");
            return false;
        }

        String query = "UPDATE User_Subject SET Score1=?, Score2=?, Score3=?, Score4=?, Score5=? WHERE UserCode=? AND SubjectCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, us.getScore1());
            stmt.setDouble(2, us.getScore2());
            stmt.setDouble(3, us.getScore3());
            stmt.setDouble(4, us.getScore4());
            stmt.setDouble(5, us.getScore5());
            stmt.setString(6, us.getUserCode());
            stmt.setString(7, us.getSubjectCode());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user subject: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUserSubject(String userCode, String subjectCode) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }

        String query = "DELETE FROM User_Subject WHERE UserCode=? AND SubjectCode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userCode);
            stmt.setString(2, subjectCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user subject: " + e.getMessage());
            return false;
        }
    }

    public boolean userSubjectExists(String userCode, String subjectCode) {
        if (connection == null) {
            System.err.println("No database connection.");
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
            System.err.println("Error checking user subject existence: " + e.getMessage());
        }
        return false;
    }

    public List<String> getFullScoreBoard() {
        List<String> result = new ArrayList<>();
        if (connection == null) {
            System.err.println("No database connection.");
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
            System.err.println("Error fetching scoreboard: " + e.getMessage());
        }
        return result;
    }
}