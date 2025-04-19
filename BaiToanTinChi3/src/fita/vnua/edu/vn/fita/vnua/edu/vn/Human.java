package fita.vnua.edu.vn;

import java.util.Scanner;

public abstract class Human 
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

    // Phương thức trừu tượng, không có phần thân
    public abstract void enterInfo(Scanner sc);

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
    
}
