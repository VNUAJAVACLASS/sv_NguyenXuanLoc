package fita.vnua.edu.vn;

import java.util.Scanner;

class Human 
{
    protected String code;
    protected String fullname;
    protected String address;

    public Human() {}

    public Human(String code)
    {
        this.code = code;
    }

    public Human(String code, String fullname)
    {
        this.code = code;
        this.fullname = fullname;
    }

    public Human(String code, String fullname, String address)
    {
        this.code = code;
        this.fullname = fullname;
        this.address = address;
    }

    public void enterInfo(Scanner sc)
    {
        System.out.print("Nhập mã: ");
        this.code = sc.nextLine(); // Đọc mã
        System.out.print("Nhập tên đầy đủ: ");
        this.fullname = sc.nextLine(); // Đọc tên đầy đủ
        System.out.print("Nhập địa chỉ: ");
        this.address = sc.nextLine(); // Đọc địa chỉ
    }

    public String getCode()
    {
        return code;
    }

    public String getFullName()
    {
        return fullname;
    }

    public String getAddress()
    {
        return address;
    }

    public void setCode(String code) 
    {
        this.code = code;
    }

    public void setFullName(String fullname)
    {
        this.fullname = fullname;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "Code: " + code + ", Full Name: " + fullname + ", Address: " + address;
    }
}
