package fita.vnua.edu.vn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student extends Human
{
	private
		String class_;
		List <Subject> subjectList = new ArrayList<>();
	public Student()
	{
		
	}
	public Student(String code)
	{
		super(code);
	}
	public Student(String code, String fullname)
	{
		super(code,fullname);
	}
	public Student(String code, String fullname, String class_)
	{
		super(code,fullname);
		this.class_ = class_;
	}
	public Student(String code, String fullname, String class_, String address)
	{
		super(code,fullname,address);
		this.class_ = class_;
	}
	public void addSubject(Subject sub)
	{
		subjectList.add(sub);
	}
	// còn 1 hàm
	public void enterInfo(Scanner sc)
	{
		super.enterInfo(sc);
		System.out.print("Enter class: ");
		this.class_ = sc.nextLine();
	}
	public String getCLass_()
	{
		return class_;
	}
	public void setClass_(String class_)
	{
		this.class_ = class_;
	}
}
