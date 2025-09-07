package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.Books;

public class BooksDAO {
	//Hàm hiển thị thông tin sách
	public List<Books> hienThi() {
		//Danh sách Sách
		List<Books> booksList = new ArrayList<>();
		String sql = "select * from books";
		try {
			//Kết nối database
			Connection conn = ConnectDb.ConnectDatabase();
			//Truy vấn SQL
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//Tạo mới
				Books books = new Books();
				//Cài đặt thuộc tính
				books.setId(rs.getInt("id"));
				books.setTitle(rs.getString("title"));
				books.setAuthor(rs.getString("author"));	
				//Thêm vào
				booksList.add(books);
			}	
		}
		catch(SQLException e) {
			System.out.println("Lỗi khi lấy danh sách Sách: "+e.getMessage());
		}
		return booksList;
	}
	
	//Hàm thêm sách
	public boolean themSach(Books books) {
		//SQL chèn
		String sql = "insert into books(title, author, price, imagePath)";
		try {
			Connection conn = ConnectDb.ConnectDatabase();
			PreparedStatement stmt = conn.prepareStatement(sql);
			//Cài đặt thuộc tính
			stmt.setString(1, books.getTitle());
			stmt.setString(2, books.getAuthor());
			stmt.setInt(3, books.getPrice());
			stmt.setString(4, books.getImagePath());
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected>0) {
				System.out.println("Thêm thành công");
			}
			else {
				System.err.println("Thêm thất bại");
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("Lỗi khi thêm Sách: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	//Hàm cập nhật
	public boolean capNhatSach(Books books) {
		//SQL cập nhật
		String sql = "update books set title=?, author=?, price=?, imagePath=? where id=?";
		try {
			Connection conn = ConnectDb.ConnectDatabase();
			PreparedStatement stmt = conn.prepareStatement(sql);
			//Cài đặt thuộc tính
			stmt.setString(0, books.getTitle());
			stmt.setString(1, books.getAuthor());
			stmt.setInt(2, books.getPrice());
			stmt.setString(3, books.getImagePath());
			stmt.setInt(4, books.getId());
			//Chạy sql
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected>0) {
				System.out.println("Cập nhật thành công");
			}
			else {
				System.out.println("Cập nhật thất bại");
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("Lỗi khi cập nhật sách: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	//Hàm xóa
	public boolean xoaSach(int id) {
		//SQL xóa
		String sql = "delete from books where id=?";
		try {
			//Kết nối
			Connection conn = ConnectDb.ConnectDatabase();
			PreparedStatement stmt = conn.prepareStatement(sql);
			//Cài đặt thuộc tính
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected>0) {
				System.out.println("Xóa thành công");
			}
			else {
				System.out.println("Xóa không thành công");
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("Lỗi khi xóa sách: "+e.getMessage());
		}
		return true;
	}
}
