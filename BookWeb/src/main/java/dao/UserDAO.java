package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDAO {

    // Hàm login: trả về User nếu đúng, null nếu sai
    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND active = 1";

        try (Connection con = ConnectDatabase.Connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRole(rs.getString("role"));
                    user.setActive(rs.getBoolean("active"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // Hàm register: trả về true nếu thành công
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, full_name, email, phone, role, active) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectDatabase.Connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());   
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setBoolean(7, user.isActive());

            int rows = ps.executeUpdate();
            return rows > 0; // true nếu thêm thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
