package fita.vnua.edu.vn;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Student extends Human 
{
    private String class_;
    private Map<String, Subject> subjectList = new HashMap<>();

    public Student() {}

    public Student(String code) {
        super(code);
    }

    public Student(String code, String fullname) {
        super(code, fullname);
    }

    public Student(String code, String fullname, String class_) {
        super(code, fullname);
        this.class_ = class_;
    }

    public Student(String code, String fullname, String class_, String address) {
        super(code, fullname, address);
        this.class_ = class_;
    }

    // Thêm môn học vào subjectList theo key (mã môn học)
    public void addSubject(String key, Subject sub) {
        subjectList.put(key, sub);
    }

    // Xoá môn học theo key (mã môn học)
    public void removeSubject(String key) {
        if (subjectList.containsKey(key)) {
            subjectList.remove(key);
            System.out.println("Môn học với mã " + key + " đã được xoá.");
        } else {
            System.out.println("Môn học với mã " + key + " không tồn tại.");
        }
    }

    // Tìm kiếm môn học theo tên
    public Subject searchSubjectByName(String subject) {
        for (Subject sub : subjectList.values()) {
            if (sub != null && sub.getSubjectName().equalsIgnoreCase(subject)) {
                return sub;
            }
        }
        return null;  
    }

    // Nhập thông tin của Student
    @Override
    public void enterInfo(Scanner sc) {
        // Nhập thông tin chung từ lớp Human
        System.out.print("Nhập mã: ");
        this.code = sc.nextLine();
        System.out.print("Nhập tên đầy đủ: ");
        this.fullname = sc.nextLine();
        System.out.print("Nhập địa chỉ: ");
        this.address = sc.nextLine();

        // Nhập thông tin riêng của lớp Student
        System.out.print("Nhập lớp: ");
        this.class_ = sc.nextLine();
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

}
