package fita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/small_db";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "2005"; 
    // Lấy kết nối tới cơ sở dữ liệu
    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                // Tải driver MySQL (không bắt buộc với JDBC 4.0 trở lên nếu driver có trong classpath)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Thiết lập kết nối mới
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Kết nối thành công!");
            } catch (ClassNotFoundException e) {
                System.err.println("Không tìm thấy driver JDBC: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            }
        }
        return connection;
    }

    // Đóng kết nối cơ sở dữ liệu
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Đã đóng kết nối.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }

    // Kiểm tra xem kết nối có bị đóng hay không
    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }
}
