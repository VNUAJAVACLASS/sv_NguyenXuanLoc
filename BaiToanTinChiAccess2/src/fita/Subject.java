package fita;

// Lớp đại diện cho một môn học
public class Subject {
    private String subjectCode;
    private String subjectName;
    private int credit;
    private double he1, he2, he3, he4, he5;

    // Hàm khởi tạo với đầy đủ thông tin
    public Subject(String subjectCode, String subjectName, int credit, double he1, double he2, double he3, double he4, double he5) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
        this.he1 = he1;
        this.he2 = he2;
        this.he3 = he3;
        this.he4 = he4;
        this.he5 = he5;
    }

    // Hàm khởi tạo với trọng số mặc định (0.2 cho mỗi trọng số)
    public Subject(String subjectCode, String subjectName, int credit) {
        this(subjectCode, subjectName, credit, 0.2, 0.2, 0.2, 0.2, 0.2);
    }

    // Các phương thức getter và setter
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }
    public double getHe1() { return he1; }
    public void setHe1(double he1) { this.he1 = he1; }
    public double getHe2() { return he2; }
    public void setHe2(double he2) { this.he2 = he2; }
    public double getHe3() { return he3; }
    public void setHe3(double he3) { this.he3 = he3; }
    public double getHe4() { return he4; }
    public void setHe4(double he4) { this.he4 = he4; }
    public double getHe5() { return he5; }
    public void setHe5(double he5) { this.he5 = he5; }

    // Kiểm tra tính hợp lệ của mã môn học
    public static boolean isValidCode(String code) {
        return code != null && !code.trim().isEmpty();
    }

    // Kiểm tra tính hợp lệ của số tín chỉ
    public static boolean isValidCredit(int credit) {
        return credit > 0;
    }

    // Kiểm tra tính hợp lệ của các trọng số (tổng phải bằng 1)
    public boolean isValidWeights() {
        double sum = he1 + he2 + he3 + he4 + he5;
        return Math.abs(sum - 1.0) < 0.0001; // Cho phép sai số nhỏ do số thực
    }

    // Chuyển đối tượng thành chuỗi để hiển thị
    @Override
    public String toString() {
        return String.format("Subject{code='%s', name='%s', credits=%d, weights=[%.1f, %.1f, %.1f, %.1f, %.1f]}",
                subjectCode, subjectName, credit, he1, he2, he3, he4, he5);
    }
}