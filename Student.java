package toiyeulaptrinh;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Lớp Student kế thừa Human
class Student extends Human 
{
	private String class_;
	private List<Subject> subjectList = new ArrayList<>();

	public Student() 
	{
		
	}
	public Student(String code) 
	{
		super(code); 
	}
	public Student(String code, String fullName) 
	{
		super(code, fullName); 
	}
	public Student(String code, String fullName, String class_) 
	{
		super(code, fullName);
		this.class_ = class_;
	}
	public Student(String code, String fullName, String class_, String address) 
	{
		super(code, fullName, address);
		this.class_ = class_;
	}
	public void addSubject(Subject subject) 
	{
		subjectList.add(subject);
	}
	public float calTermAverageMark() 
 	{
		return calTermAverageMark();
	}
	public void enterInfo(Scanner sc) 
	{
 		super.enterInfo(sc);
 		System.out.print("Lớp: ");
 		this.class_ = sc.nextLine();
	}
 	public String getClass_() 
 	{
 		return class_; 
 	}
 	public void setClass_(String class_) 
 	{
 		this.class_ = class_; 
 	}
}
