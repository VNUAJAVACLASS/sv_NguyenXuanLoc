package toiyeulaptrinh;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class HRM {
    private List<Human> hrList = new ArrayList<>();

    public HRM()
    {
    	
    }
    public void addHR(Human human) 
    {
        hrList.add(human);
    }
    public void addHR(Scanner sc) 
    {
        System.out.print("(1.Sinh Viên / 2.Giảng viên): ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            Student student = new Student();
            student.enterInfo(sc);
            addHR(student);
        } 
        else if (op == 2) {
            Lecturer lecturer = new Lecturer("", "", "");
            lecturer.enterInfo(sc);
            addHR(lecturer);
        } 
        else 
        {
            System.out.println("Giá trị nhập vào không đúng");
        }
    }
    public void printHRList() 
    {
        System.out.println("=== Danh sách nhân sự ===");
        for (Human hm : hrList)
        {
            System.out.println(hm);
        }
    }
    public void printStudentInfo()
    {
    	 System.out.println("=== Danh sách sinh viên ===");
    	    for (Human hm : hrList) 
    	    {
    	        if (hm instanceof Student)
    	        {
    	            System.out.println(hm);
    	        }
    	    }
    	
    }
    public void printLecturerInfo()
    {
    	System.out.println("=== Danh sách giảng viên ===");
	    for (Human hm : hrList) 
	    {
	        if (hm instanceof Lecturer)
	        {
	            System.out.println(hm);
	        }
	    }
	
    }
    public void searchHuman(String code) {
        for (Human hm : hrList) {
            if (hm.getCode().equals(code)) {
                System.out.println("== Found ==");
                System.out.println(hm);
                return;
            }
        }
        System.out.print("Không tìm thấy nhân sự có mã: " + code);
    }
    public void initDemoData() 
    {
    	System.out.print("=== Danh sách nhân sự có sẵn ===");
        hrList.add(new Student("001", "Lộc", "K68CNNT", "Hanoi"));
        hrList.add(new Lecturer("002", "Thắng", "BacNinh"));
    }
    public void initDemoData(Scanner sc) 
    {
	    while (true) 
	    {
	    	System.out.print("\nBạn có muốn thêm nhân sự không? (y/n): ");
	        String option = sc.nextLine();
	        if (option == "y") 
	        {
	        	this.addHR(sc);
	        }
	        else
	        	break;
	     }
    }
    public static void main(String[] args) 
    {
    	Scanner sc = new Scanner(System.in);
    	HRM hrm = new HRM();
    	hrm.initDemoData();
    	hrm.initDemoData(sc);
    	hrm.addHR(sc);
    	hrm.initDemoData(sc);
    	hrm.printHRList();
    }
}
