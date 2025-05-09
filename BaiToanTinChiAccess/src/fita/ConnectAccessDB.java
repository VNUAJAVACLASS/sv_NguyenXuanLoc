package fita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectAccessDB  {
    private static Connection connection;
    private static final String DB_URL = "jdbc:ucanaccess://C://Users//nguye//Documents//Database 13 4 2025.accdb";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                System.err.println("❌ Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}