package dao;

import model.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    private static final String DB_URL = "jdbc:derby:newsweb;create=true";
    private static final String DB_USER = "";
    private static final String DB_PASS = "";

    public NewsDAO() {
        // Tạo bảng nếu chưa tồn tại
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS news (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "content TEXT NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException("Không thể tạo bảng", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public List<News> getAllNews() throws SQLException {
        List<News> newsList = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM news")) {
            while (rs.next()) {
                newsList.add(new News(rs.getInt("id"), rs.getString("title"), rs.getString("content")));
            }
        }
        return newsList;
    }

    public News findById(int id) throws SQLException {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM news WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new News(rs.getInt("id"), rs.getString("title"), rs.getString("content"));
                }
            }
        }
        return null;
    }

    public void addNews(News news) throws SQLException {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO news (title, content) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, news.getTitle());
            pstmt.setString(2, news.getContent());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    news.setId(generatedKeys.getInt(1)); // Cập nhật ID cho đối tượng
                }
            }
        }
    }

    public void updateNews(News news) throws SQLException {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE news SET title = ?, content = ? WHERE id = ?")) {
            pstmt.setString(1, news.getTitle());
            pstmt.setString(2, news.getContent());
            pstmt.setInt(3, news.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteNews(int id) throws SQLException {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM news WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}