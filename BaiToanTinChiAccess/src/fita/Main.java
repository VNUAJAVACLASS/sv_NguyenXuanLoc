package fita;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        UserSubjectDAO userSubjectDAO = new UserSubjectDAO();

        while (true) {
            System.out.println("\n====== MENU ======");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Xoá người dùng");
            System.out.println("3. Xem danh sách người dùng");
            System.out.println("4. Thêm môn học");
            System.out.println("5. Nhập điểm cho người dùng");
            System.out.println("6. Xem bảng điểm (đầy đủ)");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Nhập mã: ");
                    String code = sc.nextLine();
                    System.out.print("Nhập tên: ");
                    String name = sc.nextLine();
                    System.out.print("Nhập địa chỉ: ");
                    String address = sc.nextLine();
                    System.out.print("Nhập lớp: ");
                    String className = sc.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String password = sc.nextLine();
                    System.out.print("Nhập vai trò (Student/Admin): ");
                    String role = sc.nextLine();
                    if (userDAO.addUser(new User(code, name, address, className, password, role))) {
                        System.out.println("✔ Đã thêm người dùng.");
                    } else {
                        System.out.println("❌ Thêm thất bại.");
                    }
                }

                case 2 -> {
                    System.out.print("Nhập mã người dùng cần xoá: ");
                    String code = sc.nextLine();
                    if (userDAO.deleteUser(code)) {
                        System.out.println("✔ Đã xoá.");
                    } else {
                        System.out.println("❌ Xoá thất bại.");
                    }
                }

                case 3 -> {
                    List<User> users = userDAO.getAllUsers();
                    System.out.println("\n--- Danh sách người dùng ---");
                    for (User u : users) {
                        System.out.println(u.getUserCode() + " | " + u.getFullName() + " | " + u.getClassName());
                    }
                }

                case 4 -> {
                    System.out.print("Nhập mã môn học: ");
                    String subjectCode = sc.nextLine();
                    System.out.print("Nhập tên môn học: ");
                    String subjectName = sc.nextLine();
                    System.out.print("Nhập số tín chỉ: ");
                    int credit = Integer.parseInt(sc.nextLine());
                    if (subjectDAO.addSubject(new Subject(subjectCode, subjectName, credit))) {
                        System.out.println("✔ Đã thêm môn học.");
                    } else {
                        System.out.println("❌ Thêm môn học thất bại.");
                    }
                }

                case 5 -> {
                    System.out.print("Nhập mã người dùng: ");
                    String userCode = sc.nextLine();
                    System.out.print("Nhập mã môn học: ");
                    String subjectCode = sc.nextLine();
                    double[] scores = new double[5];
                    for (int i = 0; i < 5; i++) {
                        System.out.print("Nhập điểm " + (i + 1) + ": ");
                        scores[i] = Double.parseDouble(sc.nextLine());
                    }
                    UserSubject us = new UserSubject(userCode, subjectCode,
                            scores[0], scores[1], scores[2], scores[3], scores[4]);
                    if (userSubjectDAO.addUserSubject(us)) {
                        System.out.println("✔ Đã thêm điểm.");
                    } else {
                        System.out.println("❌ Thêm điểm thất bại.");
                    }
                }

                case 6 -> {
                    List<String> scoreBoard = userSubjectDAO.getFullScoreBoard();
                    System.out.println("\n--- Bảng điểm đầy đủ ---");
                    for (String line : scoreBoard) {
                        System.out.println(line);
                    }
                }

                case 0 -> {
                    System.out.println("Tạm biệt!");
                    return;
                }

                default -> System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }
}
