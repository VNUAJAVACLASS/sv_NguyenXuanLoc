package fita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectAccessDB {
{
	try {
		 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		 Connection conn= DriverManager.getConnection("jdbc:ucanaccess://C://Users//nguye//Documents//Database 13 4 2025.accdb"); 
		 System.out.print(conn);
		 }catch(SQLException ex) {
		 System.out.print(ex.toString());
		 } 
	catch (ClassNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 }

public static Connection getConnection() {
	// TODO Auto-generated method stub
	return null;
}
}