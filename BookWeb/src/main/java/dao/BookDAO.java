package dao;

import model.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection conn = ConnectDatabase.Connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book(
                    String.valueOf(rs.getInt("id")),
                    rs.getString("name"),
                    rs.getString("author"),
                    rs.getInt("price"),
                    rs.getString("imagePath")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM Book WHERE id=?";
        try (Connection conn = ConnectDatabase.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                    String.valueOf(rs.getInt("id")),
                    rs.getString("name"),
                    rs.getString("author"),
                    rs.getInt("price"),
                    rs.getString("imagePath")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO Book(name,author,price,imagePath) VALUES(?,?,?,?)";
        try (Connection conn = ConnectDatabase.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPrice());
            ps.setString(4, book.getImagePath());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        String sql = "UPDATE Book SET name=?, author=?, price=?, imagePath=? WHERE id=?";
        try (Connection conn = ConnectDatabase.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPrice());
            ps.setString(4, book.getImagePath());
            ps.setInt(5, Integer.parseInt(book.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM Book WHERE id=?";
        try (Connection conn = ConnectDatabase.Connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
