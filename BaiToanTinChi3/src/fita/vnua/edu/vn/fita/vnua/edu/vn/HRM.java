package fita.vnua.edu.vn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lớp HRM (Human Resource Management) quản lý danh sách nhân sự
public class HRM {
    private List<Human> hrList; // Danh sách nhân sự
    
    // Hàm khởi tạo danh sách nhân sự
    public HRM() {
        hrList = new ArrayList<Human>();
    }
    
    // Hàm tv thêm nhân sự vào danh sách
    public void addHR(Human hm) {
        hrList.add(hm);
    }
    
    // Hàm tv nhập nhân sự từ bàn phím và thêm vào danh sách
    public void addHR(Scanner sc) {
        int loai;
        System.out.println("Chon loai nhan su (sv: 0, gv:1): ");
        loai = sc.nextInt(); sc.nextLine();
        
        Human human = null;
        if (loai == 0) {
            human = new Student(); // Nếu chọn 0, tạo đối tượng Student
        } else if (loai == 1) {
            human = new Lecturer(); // Nếu chọn 1, tạo đối tượng Lecturer
        }
        
        if (human != null) {
            human.enterInfo(sc); // Nhập thông tin nhân sự
            addHR(human); // Thêm vào danh sách
        }
    }
    
    // Hàm tv hiển thị danh sách nhân sự
    public void printHRList() {
        for (Human human : hrList) {
            System.out.println(human.toString());
        }
    }
    
    // Hàm tv hiển thị danh sách sinh viên cùng lớp của họ
    public void printStudentList() {
        for (Human human : hrList) {
            if (human instanceof Student) {
                Student std = (Student) human;
                System.out.println(std.getClass());
            }
        }
    }
    
    // Hàm tv khởi tạo dữ liệu mẫu
    public void initDemoData() {
        Human h1 = new Student("680373", "Vuong Nhat Thanh", "K68CNTTC");
        Human h2 = new Lecturer("600000", "Ngo Cong Thang", "Ha Noi");
        
        addHR(h1);
        addHR(h2);
    }
    
    // Hàm tv nhập dữ liệu mẫu từ người dùng
    public void initDemoData(Scanner sc) {
        String chon;
        do {
            addHR(sc);
            System.out.println("Ban muon nhap tiep (c/k): ");
            chon = sc.nextLine();
        } while ("c".equalsIgnoreCase(chon));
    }
    
    // Hàm tv tìm kiếm nhân sự theo mã
    public List<Human> searchHuman(String code) {
        List<Human> humanList = new ArrayList<Human>();
        for (Human human : hrList) {
            if (human.code.contains(code)) {
                humanList.add(human);
            }
        }
        return humanList;
    }
    
    // main để chạy chương trình
    public static void main(String[] args) {
        HRM hrm = new HRM();
        hrm.initDemoData();
        Scanner sc = new Scanner(System.in);
        hrm.initDemoData(sc);
        hrm.printHRList();
        hrm.searchHuman("68");
    }
}