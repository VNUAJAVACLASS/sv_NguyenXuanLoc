package fita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private final Connection connection;

    public SubjectDAO() {
        connection = ConnectAccessDB.getConnection();
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        if (connection == null) {
            System.err.println("No database connection.");
            return subjects;
        }

        String query = "SELECT * FROM Subject";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
            System.err.println("Error fetching subjects: " + e.getMessage());
        }
        return subjects;
    }

    public boolean addSubject(Subject subject) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!Subject.isValidCode(subject.getSubjectCode()) || !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Invalid subject code, credits, or weights.");
            return false;
        }
        if (subjectExists(subject.getSubjectCode())) {
            System.err.println("Subject code already exists: " + subject.getSubjectCode());
            return false;
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
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding subject: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSubject(Subject subject) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!Subject.isValidCode(subject.getSubjectCode()) || !Subject.isValidCredit(subject.getCredit()) ||
            !subject.isValidWeights()) {
            System.err.println("Invalid subject code, credits, or weights.");
            return false;
        }

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
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating subject: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSubject(String subjectCode) {
        if (connection == null) {
            System.err.println("No database connection.");
            return false;
        }
        if (!Subject.isValidCode(subjectCode)) {
            System.err.println("Invalid subject code.");
            return false;
        }

        String query = "DELETE FROM Subject WHERE Subjectcode=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subjectCode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting subject: " + e.getMessage());
            return false;
        }
    }

    public boolean subjectExists(String subjectCode) {
        if (connection == null) {
            System.err.println("No database connection.");
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
            System.err.println("Error checking subject existence: " + e.getMessage());
        }
        return false;
    }
}