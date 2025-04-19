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
		System.out.print("Nhập mã sinh viên để thêm môn học và nhập điểm: ");
		String code = sc.nextLine();
		
		// Tìm sinh viên
		Student targetStudent = null;
		for (Human hm : hrm.hrList) {
			if (hm instanceof Student && hm.getCode().equals(code)) {
				targetStudent = (Student) hm;
				break;
			}
		}

		if (targetStudent != null) {
			System.out.print("Bạn muốn thêm bao nhiêu môn học? ");
			int soMon = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < soMon; i++) {
				System.out.println("Nhập thông tin môn học thứ " + (i + 1));
				System.out.print("Mã môn: ");
				String subjectCode = sc.nextLine();
				System.out.print("Tên môn: ");
				String subjectName = sc.nextLine();
				System.out.print("Điểm quá trình: ");
				double dqt = Double.parseDouble(sc.nextLine());
				System.out.print("Điểm thi: ");
				double dt = Double.parseDouble(sc.nextLine());

				// Tạo đối tượng Subject (giả sử bạn có lớp Subject có hàm set/get phù hợp)
				Subject s = new Subject(subjectCode, subjectName, dqt, dt);
				targetStudent.addSubject(subjectCode, s);
			}

			// In lại thông tin sinh viên và điểm các môn học
			System.out.println("\nThông tin sinh viên:");
			System.out.println(targetStudent);
			System.out.println("Danh sách môn học:");
			for (Subject s : targetStudent.getSubjectList().values()) {
				System.out.println(s.getSubjectCode() + " - " + s.getSubjectName() + 
					" | DQT: " + s.getProcessScore() + 
					" | Điểm thi: " + s.getTestScore() + 
					" | Điểm tổng kết: " + s.calculateFinalScore());
			}
		} else {
			System.out.println("Không tìm thấy sinh viên với mã " + code);
		}

		System.out.println("===========");
		sc.close();
	}

}
