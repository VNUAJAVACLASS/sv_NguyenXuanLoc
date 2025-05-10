package fita;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Đọc file HTML
        DocHTML docHTML = new DocHTML();
        List<LichHoc> danhSachLichHoc = docHTML.docThoiKhoaBieu("src/main/resources/timetable.html");

        // Tạo thời khóa biểu
        ThoiKhoaBieu thoiKhoaBieu = new ThoiKhoaBieu();
        for (LichHoc lich : danhSachLichHoc) {
            thoiKhoaBieu.themLichHoc(lich);
        }

        // Menu tương tác
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== QUẢN LÝ THỜI KHÓA BIỂU ===");
            System.out.println("1. Hiển thị toàn bộ thời khóa biểu");
            System.out.println("2. Tìm lịch học theo môn");
            System.out.println("3. Tìm lịch học theo ngày (thứ)");
            System.out.println("4. Tìm lịch học theo tuần");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Xóa bộ đệm
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                scanner.nextLine(); // Xóa bộ đệm
                continue;
            }

            switch (choice) {
                case 1:
                    thoiKhoaBieu.hienThiThoiKhoaBieu();
                    break;
                case 2:
                    System.out.print("Nhập tên môn học: ");
                    String tenMon = scanner.nextLine();
                    List<LichHoc> ketQuaMon = thoiKhoaBieu.timLichHocTheoMon(tenMon);
                    if (ketQuaMon.isEmpty()) {
                        System.out.println("Không tìm thấy lịch học cho môn: " + tenMon);
                    } else {
                        System.out.println("Lịch học cho môn " + tenMon + ":");
                        ketQuaMon.forEach(lichHoc -> System.out.println("\t" + lichHoc));
                    }
                    break;
                case 3:
                    System.out.print("Nhập thứ (2-7, ví dụ: 2 cho Thứ 2): ");
                    String ngay = scanner.nextLine();
                    if (!ngay.matches("[2-7]")) {
                        System.out.println("Thứ phải là số từ 2 đến 7!");
                        continue;
                    }
                    List<LichHoc> ketQuaNgay = thoiKhoaBieu.timLichHocTheoThu(ngay);
                    if (ketQuaNgay.isEmpty()) {
                        System.out.println("Không có lịch học vào thứ: " + ngay);
                    } else {
                        System.out.println("Lịch học vào thứ " + ngay + ":");
                        ketQuaNgay.forEach(lichHoc -> System.out.println("\t" + lichHoc));
                    }
                    break;
                case 4:
                    System.out.print("Nhập số tuần (1-20, ví dụ: 1, 2, ...): ");
                    int soTuan;
                    try {
                        soTuan = scanner.nextInt();
                        scanner.nextLine(); // Xóa bộ đệm
                        if (soTuan < 1 || soTuan > 20) {
                            System.out.println("Số tuần phải từ 1 đến 20!");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Vui lòng nhập số tuần hợp lệ!");
                        scanner.nextLine();
                        continue;
                    }
                    Tuan tuan = thoiKhoaBieu.timLichHocTheoTuan(soTuan);
                    if (tuan == null || tuan.getDanhSachThu().stream().allMatch(th -> th.getDanhSachLichHoc().isEmpty())) {
                        System.out.println("Không có lịch học trong tuần " + soTuan);
                    } else {
                        System.out.println(tuan);
                    }
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}