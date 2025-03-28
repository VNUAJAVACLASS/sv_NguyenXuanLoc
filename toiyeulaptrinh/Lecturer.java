package toiyeulaptrinh;

import java.util.Scanner;

//Lớp Lecturer kế thừa Human
class Lecturer extends Human 
{
	private String password;

	public Lecturer(String code, String password) 
	{
		super(code);
		this.password = password;
	}
	public Lecturer(String code, String fullName, String address) 
	{
		super(code, fullName, address);
	}
	public void enterInfo(Scanner sc) 
	{
		super.enterInfo(sc);
		System.out.print("Enter password: ");
		this.password = sc.nextLine();
	}
}

