package fita.vnua.edu.vn;

import java.util.Scanner;

class Lecturer extends Human
{
	private String password;
	public Lecturer()
	{
		
	}
	public Lecturer(String code, String password)
	{
		super(code);
		this.password = password;
	}
	public Lecturer(String code, String password, String address)
	{
		super(code,address);
		this.password = password;
	}
	@Override
	public void enterInfo(Scanner sc)
	{
		super.enterInfo(sc);
		System.out.print("Enter password: ");
		this.password = sc.next();
	}

}
