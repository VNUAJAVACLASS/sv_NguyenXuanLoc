package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
	public static Connection Connect() {
		String url = "jdbc:mysql://127.0.0.1:3306/BookWeb?useSSL=false&serverTimezone=UTC";
		String user = "root";
		String password = "2005";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			con = DriverManager.getConnection(url, user, password);
		}
		catch(Exception e) {
			System.out.println("Cant connect to database:"+e);
			e.printStackTrace();
		}
		return con;
	}
}
