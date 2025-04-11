package toiyeulaptrinh;

import java.util.Scanner;

class Human {
    protected String address;
    protected String code;
    protected String fullName;

    public Human() 
    {
    	
    }
    public Human(String code) 
    {
    	this.code = code; 
    }
    public Human(String code, String fullname) 
    {
        this.code = code;
        this.fullName = fullname;
    }
    public Human(String code, String fullname, String address) 
    {
        this.code = code;
        this.fullName = fullname;
        this.address = address;
    }
    public void enterInfo(Scanner sc) 
    {
        System.out.print("Mã: ");
        this.code = sc.nextLine();
        System.out.print("Họ tên: ");
        this.fullName = sc.nextLine();
        System.out.print("Địa chỉ: ");
        this.address = sc.nextLine();
    }
    public String getAddress() 
    { 
    	return address; 
    }
    public String getCode() 
    {
    	return code; 
    }
    public String getFullname() 
    {
    	return fullName; 
    }
    public void setAddress(String address) 
    {
    	this.address = address; 
    }
    public void setCode(String code) 
    {
    	this.code = code; 
    }
    public void setFullname(String fullName) 
    {
    	this.fullName = fullName; 
    }
    public String toString() 
    {
        return "Mã: " + code + ", Họ tên: " + fullName + ", Địa chỉ: " + address;
    }
}

