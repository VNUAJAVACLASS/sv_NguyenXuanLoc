package fita;

import java.util.List;
import java.util.Scanner;

// Lớp chính để chạy chương trình quản lý điểm số
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        UserSubjectDAO userSubjectDAO = new UserSubjectDAO();

        // Vòng lặp chính để hiển thị menu và xử lý lựa chọn của người dùng
        while (true) {
            System.out.println("\n====== MENU ======");
            System.out.println("1. Thêm người dùng");
            System.out.println("2. Xoá người dùng");
            System.out.println("3. Xem danh sách người dùng");
            System.out.println("4. Thêm môn học");
            System.out.println("5. Nhập điểm cho người dùng");
            System.out.println("6. Xem bảng điểm (đầy đủ)");
            System.out.println("7. Cập nhật người dùng");
            System.out.println("8. Cập nhật điểm cho người dùng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    // Thêm người dùng mới
                    System.out.println("Chọn vai trò: 1 - Student, 2 - Lecturer");
                    System.out.print("Nhập lựa chọn (1 hoặc 2): ");
                    String roleChoice;
                    try {
                        roleChoice = sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    }

                    String role;
                    boolean isStudent;
                    if (roleChoice.equals("1")) {
                        role = "Student";
                        isStudent = true;
                    } else if (roleChoice.equals("2")) {
                        role = "Lecturer";
                        isStudent = false;
                    } else {
                        System.out.println("Lựa chọn vai trò không hợp lệ. Phải là 1 hoặc 2.");
                        break;
                    }

                    System.out.print("Nhập mã: ");
                    String code = sc.nextLine();
                    if (!User.isValidCode(code)) {
                        System.out.println("Mã không được để trống.");
                        break;
                    }
                    System.out.print("Nhập tên (chỉ chứa chữ và khoảng trắng): ");
                    String name = sc.nextLine();
                    if (!User.isValidName(name)) {
                        System.out.println("Tên không hợp lệ (chỉ chứa chữ, khoảng trắng, hoặc dấu gạch nối).");
                        break;
                    }
                    System.out.print("Nhập địa chỉ: ");
                    String address = sc.nextLine();
                    if (!User.isValidAddress(address)) {
                        System.out.println("Địa chỉ không được để trống.");
                        break;
                    }

                    String className = null;
                    String password = null;
                    if (isStudent) {
                        System.out.print("Nhập lớp (định dạng K + số, ví dụ: K01): ");
                        className = sc.nextLine();
                        if (!User.isValidClassName(className)) {
                            System.out.println("Lớp không hợp lệ (phải bắt đầu bằng K và theo sau là số).");
                            break;
                        }
                    } else {
                        System.out.print("Nhập mật khẩu: ");
                        password = sc.nextLine();
                        if (!User.isValidPassword(password)) {
                            System.out.println("Mật khẩu không được để trống.");
                            break;
                        }
                    }

                    User newUser = new User(code, name, address, className, password, role);
                    if (userDAO.addUser(newUser)) {
                        System.out.println("Đã thêm người dùng.");
                    } else {
                        System.out.println("Thêm thất bại. Mã người dùng có thể đã tồn tại hoặc có lỗi dữ liệu.");
                    }
                }

                case 2 -> {
                    // Xóa người dùng
                    System.out.print("Nhập mã người dùng cần xoá: ");
                    String code = sc.nextLine();
                    if (!User.isValidCode(code)) {
                        System.out.println("Mã không được để trống.");
                        break;
                    }
                    if (userDAO.deleteUser(code)) {
                        System.out.println("Đã xoá.");
                    } else {
                        System.out.println("Xoá thất bại. Mã người dùng không tồn tại, có điểm liên quan, hoặc có lỗi.");
                    }
                }

                case 3 -> {
                    // Hiển thị danh sách người dùng
                    List<User> users = userDAO.getAllUsers();
                    System.out.println("\n--- Danh sách người dùng ---");
                    if (users.isEmpty()) {
                        System.out.println("Không có người dùng nào.");
                    } else {
                        // In tiêu đề bảng
                        System.out.printf("%-10s | %-15s | %-20s | %-10s | %-15s | %-10s%n",
                                "Code", "Fullname", "Address", "Class", "Password", "Role");
                        System.out.println("-".repeat(90));
                        // In từng người dùng
                        for (User u : users) {
                            String className = u.getClassName() != null ? u.getClassName() : "";
                            String password = u.getPassword() != null ? "****" : "";
                            String address = u.getAddress() != null ? u.getAddress() : "";
                            String role = u.getRole() != null ? u.getRole() : "";
                            // Cắt ngắn giá trị dài
                            String fullName = u.getFullName().length() > 12 ?
                                    u.getFullName().substring(0, 9) + "..." : u.getFullName();
                            address = address.length() > 17 ? address.substring(0, 14) + "..." : address;
                            System.out.printf("%-10s | %-15s | %-20s | %-10s | %-15s | %-10s%n",
                                    u.getUserCode(), fullName, address, className, password, role);
                        }
                    }
                }

                case 4 -> {
                    // Thêm môn học mới
                    System.out.print("Nhập mã môn học: ");
                    String subjectCode = sc.nextLine();
                    if (!Subject.isValidCode(subjectCode)) {
                        System.out.println("Mã môn học không được để trống.");
                        break;
                    }
                    System.out.print("Nhập tên môn học: ");
                    String subjectName = sc.nextLine();
                    if (subjectName.trim().isEmpty()) {
                        System.out.println("Tên môn học không được để trống.");
                        break;
                    }
                    System.out.print("Nhập số tín chỉ: ");
                    int credit;
                    try {
                        credit = Integer.parseInt(sc.nextLine());
                        if (!Subject.isValidCredit(credit)) {
                            System.out.println("Số tín chỉ phải lớn hơn 0.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số tín chỉ phải là số hợp lệ.");
                        break;
                    }
                    Subject newSubject = new Subject(subjectCode, subjectName, credit, 0.2, 0.2, 0.2, 0.2, 0.2);
                    if (subjectDAO.addSubject(newSubject)) {
                        System.out.println("Đã thêm môn học.");
                    } else {
                        System.out.println("Thêm môn học thất bại. Mã môn học có thể đã tồn tại hoặc có lỗi.");
                    }
                }

                case 5 -> {
                    // Nhập điểm cho sinh viên
                    System.out.print("Nhập mã người dùng: ");
                    String userCode = sc.nextLine();
                    if (!userDAO.userExists(userCode)) {
                        System.out.println("Mã người dùng không tồn tại.");
                        break;
                    }
                    if (!userDAO.isStudent(userCode)) {
                        System.out.println("Chỉ có thể nhập điểm cho sinh viên.");
                        break;
                    }
                    System.out.print("Nhập mã môn học: ");
                    String subjectCode = sc.nextLine();
                    if (!subjectDAO.subjectExists(subjectCode)) {
                        System.out.println("Mã môn học không tồn tại.");
                        break;
                    }
                    double[] scores = new double[5];
                    for (int i = 0; i < 5; i++) {
                        boolean validScore = false;
                        while (!validScore) {
                            System.out.print("Nhập điểm " + (i + 1) + ": ");
                            try {
                                scores[i] = Double.parseDouble(sc.nextLine());
                                if (!UserSubject.isValidScore(scores[i])) {
                                    System.out.println("Điểm phải từ 0 đến 10. Vui lòng nhập lại.");
                                } else {
                                    validScore = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Điểm phải là số hợp lệ. Vui lòng nhập lại.");
                            }
                        }
                    }
                    UserSubject us = new UserSubject(userCode, subjectCode,
                            scores[0], scores[1], scores[2], scores[3], scores[4]);
                    if (userSubjectDAO.addUserSubject(us)) {
                        System.out.println("Đã thêm điểm.");
                    } else {
                        System.out.println("Thêm điểm thất bại. Có thể sinh viên đã có điểm cho môn này hoặc có lỗi.");
                    }
                }

                case 6 -> {
                    // Hiển thị bảng điểm đầy đủ
                    List<String> scoreBoard = userSubjectDAO.getFullScoreBoard();
                    System.out.println("\n--- Bảng điểm đầy đủ ---");
                    if (scoreBoard.isEmpty()) {
                        System.out.println("Không có dữ liệu điểm.");
                    } else {
                        for (String line : scoreBoard) {
                            System.out.println(line);
                        }
                    }
                }

                case 7 -> {
                    // Cập nhật thông tin người dùng
                    System.out.print("Nhập mã người dùng cần cập nhật: ");
                    String code = sc.nextLine();
                    if (!userDAO.userExists(code)) {
                        System.out.println("Mã người dùng không tồn tại.");
                        break;
                    }
                    System.out.println("Chọn vai trò: 1 - Student, 2 - Lecturer");
                    System.out.print("Nhập lựa chọn (1 hoặc 2): ");
                    String roleChoice;
                    try {
                        roleChoice = sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    }
                    String role;
                    boolean isStudent;
                    if (roleChoice.equals("1")) {
                        role = "Student";
                        isStudent = true;
                    } else if (roleChoice.equals("2")) {
                        role = "Lecturer";
                        isStudent = false;
                    } else {
                        System.out.println("Lựa chọn vai trò không hợp lệ.");
                        break;
                    }
                    System.out.print("Nhập tên mới (chỉ chứa chữ và khoảng trắng): ");
                    String name = sc.nextLine();
                    if (!User.isValidName(name)) {
                        System.out.println("Tên không hợp lệ (chỉ chứa chữ, khoảng trắng, hoặc dấu gạch nối).");
                        break;
                    }
                    System.out.print("Nhập địa chỉ mới: ");
                    String address = sc.nextLine();
                    if (!User.isValidAddress(address)) {
                        System.out.println("Địa chỉ không được để trống.");
                        break;
                    }
                    String className = null;
                    String password = null;
                    if (isStudent) {
                        System.out.print("Nhập lớp mới (định dạng K + số, ví dụ: K01): ");
                        className = sc.nextLine();
                        if (!User.isValidClassName(className)) {
                            System.out.println("Lớp không hợp lệ (phải bắt đầu bằng K và theo sau là số).");
                            break;
                        }
                    } else {
                        System.out.print("Nhập mật khẩu mới: ");
                        password = sc.nextLine();
                        if (!User.isValidPassword(password)) {
                            System.out.println("Mật khẩu không được để trống.");
                            break;
                        }
                    }
                    User updatedUser = new User(code, name, address, className, password, role);
                    if (userDAO.updateUser(updatedUser)) {
                        System.out.println("Đã cập nhật người dùng.");
                    } else {
                        System.out.println("Cập nhật thất bại.");
                    }
                }

                case 8 -> {
                    // Cập nhật điểm số cho sinh viên
                    System.out.print("Nhập mã người dùng: ");
                    String userCode = sc.nextLine();
                    if (!userDAO.userExists(userCode)) {
                        System.out.println("Mã người dùng không tồn tại.");
                        break;
                    }
                    if (!userDAO.isStudent(userCode)) {
                        System.out.println("Chỉ có thể cập nhật điểm cho sinh viên.");
                        break;
                    }
                    System.out.print("Nhập mã môn học: ");
                    String subjectCode = sc.nextLine();
                    if (!subjectDAO.subjectExists(subjectCode)) {
                        System.out.println("Mã môn học không tồn tại.");
                        break;
                    }
                    if (!userSubjectDAO.userSubjectExists(userCode, subjectCode)) {
                        System.out.println("Người dùng chưa có điểm cho môn học này.");
                        break;
                    }
                    double[] scores = new double[5];
                    for (int i = 0; i < 5; i++) {
                        boolean validScore = false;
                        while (!validScore) {
                            System.out.print("Nhập điểm " + (i + 1) + ": ");
                            try {
                                scores[i] = Double.parseDouble(sc.nextLine());
                                if (!UserSubject.isValidScore(scores[i])) {
                                    System.out.println("Điểm phải từ 0 đến 10. Vui lòng nhập lại.");
                                } else {
                                    validScore = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Điểm phải là số hợp lệ. Vui lòng nhập lại.");
                            }
                        }
                    }
                    UserSubject us = new UserSubject(userCode, subjectCode,
                            scores[0], scores[1], scores[2], scores[3], scores[4]);
                    if (userSubjectDAO.updateUserSubject(us)) {
                        System.out.println("Đã cập nhật điểm.");
                    } else {
                        System.out.println("Cập nhật điểm thất bại.");
                    }
                }

                case 0 -> {
                    // Thoát chương trình
                    System.out.println("Tạm biệt!");
                    ConnectDB.closeConnection();
                    sc.close();
                    return;
                }

                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}