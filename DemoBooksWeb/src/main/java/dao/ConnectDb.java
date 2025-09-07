package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
	
	//Hàm kết nối
	public static Connection ConnectDatabase()
	{
	
		String url = "jdbc:mysql://localhost:3306/DemoBooksWeb";
		String user = "root";
		String password = "2005";
		
		Connection conn = null;
		
		try {
			//Tạo kết nối
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Kết nối thành công MySQL");
			
		}
		catch(SQLException e) {
			//Nếu có lỗi 
			System.out.println("Kết nối thất bại MySQL");
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
