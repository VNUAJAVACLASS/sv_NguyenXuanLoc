package fita.vnua.edu.vn;

import java.util.Scanner;

class Lecturer extends Human
{
    private String password;

    public Lecturer() {}

    public Lecturer(String code, String password)
    {
        super(code);
        this.password = password;
    }

    public Lecturer(String code, String password, String address)
    {
        super(code, address);
        this.password = password;
    }

    @Override
    public void enterInfo(Scanner sc)
    {
        // Nhập thông tin chung từ lớp Human
        System.out.print("Nhập mã: ");
        this.code = sc.nextLine();
        System.out.print("Nhập tên đầy đủ: ");
        this.fullname = sc.nextLine();
        System.out.print("Nhập địa chỉ: ");
        this.address = sc.nextLine();
        
        
        // Nhập thông tin riêng của lớp Lecturer
        System.out.print("Nhập mật khẩu: ");
        this.password = sc.nextLine();  // Dùng nextLine() để đọc mật khẩu là chuỗi.
    }

}
