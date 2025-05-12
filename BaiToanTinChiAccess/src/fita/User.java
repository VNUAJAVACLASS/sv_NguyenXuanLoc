package fita;

public class User {
    private String userCode;
    private String fullName;
    private String address;
    private String className;
    private String password;
    private String role;

    public User(String userCode, String fullName, String address, String className, String password, String role) {
        this.userCode = userCode;
        this.fullName = fullName;
        this.address = address;
        this.className = className;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
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

    // Validation methods
    public static boolean isValidCode(String code) {
        return code != null && !code.trim().isEmpty();
    }

    public static boolean isValidRole(String role) {
        return role != null && (role.equalsIgnoreCase("Student") || 
                                role.equalsIgnoreCase("Lecturer"));
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Chỉ cho phép chữ cái, khoảng trắng, và dấu gạch nối
        return name.matches("^[a-zA-Z\\s-]+$");
    }

    public static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    public static boolean isValidClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return false;
        }
        // Phải bắt đầu bằng "K" và theo sau là số (ví dụ: K01, K123)
        return className.matches("^K\\d+$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty();
    }

    public boolean isValidForRole() {
        if (role.equalsIgnoreCase("Student")) {
            return isValidClassName(className) && password == null;
        } else if (role.equalsIgnoreCase("Lecturer")) {
            return isValidPassword(password) && className == null;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("User{code='%s', name='%s', class='%s', role='%s'}",
                userCode, fullName, className, role);
    }
}