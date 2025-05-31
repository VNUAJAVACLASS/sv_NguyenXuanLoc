package fita;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @Column(name = "Code")
    private String userCode;
    
    @Column(name = "Fullname", nullable = false)
    private String fullName;
    
    @Column(name = "Address", nullable = false)
    private String address;
    
    @Column(name = "Class")
    private String className;
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "Role", nullable = false)
    private String role;

    // Constructor mặc định
    public User() {}

    // Constructor đầy đủ
    public User(String userCode, String fullName, String address, String className, String password, String role) {
        this.userCode = userCode;
        this.fullName = fullName;
        this.address = address;
        this.className = className;
        this.password = password;
        this.role = role;
    }

    // Getter và setter
    public String getUserCode() { return userCode; }
    public void setCode(String userCode) { this.userCode = userCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Kiểm tra tính hợp lệ của mã người dùng
    public static boolean isValidCode(String code) {
        return code != null && !code.trim().isEmpty();
    }

    // Kiểm tra tính hợp lệ của vai trò (Student hoặc Lecturer)
    public static boolean isValidRole(String role) {
        return role != null && (role.equalsIgnoreCase("Student") || 
                                role.equalsIgnoreCase("Lecturer"));
    }

    // Kiểm tra tính hợp lệ của tên
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[a-zA-Z\\s-]+$");
    }

    // Kiểm tra tính hợp lệ của địa chỉ
    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    // Kiểm tra tính hợp lệ của tên lớp
    public static boolean isValidClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return true; // Cho phép null cho Lecturer
        }
        return className.matches("^K\\d+$");
    }

    // Kiểm tra tính hợp lệ của mật khẩu
    public static boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty();
    }

    // Kiểm tra tính hợp lệ của dữ liệu dựa trên vai trò
    public boolean isValidForRole() {
        if (role.equalsIgnoreCase("Student")) {
            return className != null && isValidClassName(className) && password == null;
        } else if (role.equalsIgnoreCase("Lecturer")) {
            return password != null && isValidPassword(password) && className == null;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("User{code='%s', name='%s', class='%s', role='%s'}",
                userCode, fullName, className, role);
    }
}