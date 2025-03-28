package toiyeulaptrinh;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class HRM {
    private List<Human> hrList = new ArrayList<>();

    public void addHR(Human human) 
    {
        hrList.add(human);
    }
    public void addHR(Scanner sc) {
        System.out.print("Enter type (1.Student/2.Lecturer): ");
        int op = sc.nextInt();
        if (op == 1) 
        {
            Student s = new Student();
            s.enterInfo(sc);
            addHR(s);
        } 
        else if (op == 2) 
        {
            Lecturer l = new Lecturer("", "","");
            l.enterInfo(sc);
            addHR(l);
        }
    }
    public void printHRList() 
    {
        for (Human hm : hrList) System.out.println(hm);
    }
    public void searchHuman(String code) 
    {
        for (Human hm : hrList) 
        {
            if (hm.getCode().equals(code)) 
            {
                System.out.println(hm);
                return;
            }
        }
        System.out.println("Không tìm thấy");
    }
    public void initDemoData() 
    {
        hrList.add(new Student("001", "Lộc", "K68CNNT", "Hanoi"));
        hrList.add(new Lecturer("002", "Thắng", "BacNinh"));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Danh sách nhân sự:\n");
        
        //hiện ra những nhân sự đã nạp sẵn dữ liệu
        HRM hrm = new HRM();
        hrm.initDemoData();
        hrm.printHRList();
        
        
        //Nhập thêm nhân sự
        System.out.print("Bạn có muốn thêm nhân sự không? y/n: ");
        String option = sc.next();
        if (option.equals ("y"))
        {
        	hrm.addHR(sc);
        }
        
        //Tìm kiếm hoặc thoát
        else if (option.equals("n"))
        {
        	System.out.print("1.Tìm kiếm 2.Thoát: ");
        	String option1 = sc.next();
        	if (option1.equals ("1"))
        	{
        		
        		hrm.searchHuman("001");
        	}
        	else
        	{
        		
        		System.out.print("OK");
        	}
        }
        
        sc.close();
    }
}