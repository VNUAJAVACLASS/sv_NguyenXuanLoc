package fita;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            CtrinhChinh ctrinhChinh = new CtrinhChinh();
            DocHTML docHTML = new DocHTML(ctrinhChinh);
            docHTML.khoiTaoVaDocFileHTML("src/tkb1.html");
            ctrinhChinh.hienThiMenuVaXuLyLuaChon(scanner);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file HTML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
