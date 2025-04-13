package fita.vnua.edu.vn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HRM 
{
	private List <Human> hrList = new ArrayList<>();

	public HRM() {}

	public void addHR(Human hm)
	{
		hrList.add(hm);
	}

	public void addHR(Scanner sc)
	{
		int option ;
		System.out.print("Bạn muốn thêm sinh viên hay giảng viên (1.Sinh viên 2.Giảng viên): ");
		option = sc.nextInt();
		sc.nextLine(); 

		if(option == 1)
		{
			Student student = new Student();
			student.enterInfo(sc);
			addHR(student);  
		}
		else if(option == 2)
		{
			Lecturer lecturer = new Lecturer();
			lecturer.enterInfo(sc);
			addHR(lecturer); 
		}
		else
		{
			System.out.println("Lựa chọn không hợp lệ.");
		}
	}

	public void printHRList()
	{
		System.out.println("Danh sách nhân sự:");
		for(Human hm : hrList)
		{
			System.out.println(hm);
		}
	}

	public void printLecturerInfo()
	{
		System.out.println("Danh sách giảng viên:");
		for(Human hm : hrList)
		{
			if(hm instanceof Lecturer)
			{
				System.out.println(hm);
			}
		}
	}

	public void printStudentInfo()
	{
		System.out.println("Danh sách sinh viên:");
		for(Human hm : hrList)
		{
			if(hm instanceof Student)
			{
				System.out.println(hm);
			}
		}
	}

	public void searchHuman(String code)
	{
		int kiemtra = 0;
		for (Human hm : hrList) 
		{
            if (hm.getCode().equals(code)) 
            {
                System.out.println("Kết quả tìm kiếm:");
                System.out.println(hm);
                kiemtra = 1;
                break;
            }
		}
		if(kiemtra == 0)
			System.out.println("Mã không tồn tại.");
	}

	public void initDemoData()
	{
		hrList.add(new Student("001", "Lộc", "K68CNNT", "Hanoi"));
        hrList.add(new Lecturer("002", "Thắng", "BacNinh"));
	}

	public void initDemoData(Scanner sc)
	{
		while (true) 
	    {
	    	System.out.print("\nBạn có muốn thêm nhân sự không? (y/n): ");
	        String option = sc.next();
	        sc.nextLine();

	        if (option.equals("y")) 
	        {
	        	addHR(sc);
	        }
	        else
	        	break;
	    }
	}

	public static void main(String[] args)
	{
		System.out.println("Bài toán tín chỉ");
		HRM hrm = new HRM();
		Scanner sc = new Scanner(System.in);

		// Hiển thị danh sách có sẵn
		hrm.initDemoData();
		
		// Thêm nhân sự
		hrm.initDemoData(sc);

		// In toàn bộ danh sách giảng viên
        hrm.printLecturerInfo();

        // In toàn bộ danh sách sinh viên
        hrm.printStudentInfo();

        // In toàn bộ danh sách nhân sự
        hrm.printHRList();

        // Tìm kiếm
		System.out.print("Nhập mã cần tìm: ");
        String code = sc.nextLine();
        hrm.searchHuman(code);
        
        System.out.println("===========");

        sc.close();
	}
}
