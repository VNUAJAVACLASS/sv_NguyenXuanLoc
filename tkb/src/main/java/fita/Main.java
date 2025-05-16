package fita;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
    	
        // Đọc file HTML
        DocHTML docHTML = new DocHTML();
        List<LichHoc> danhSachLichHoc = docHTML.docThoiKhoaBieu("src/main/resources/timetable.html");

        // Tạo thời khóa biểu với ngày bắt đầu học kỳ cố định
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
            System.out.println("5. Xem lịch học hôm nay");
            System.out.println("6. Xem lịch học tuần này");
            System.out.println("7. Xem lịch học một ngày cụ thể");
            System.out.println("8. Thoát");
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
                    // Xem lịch học hôm nay
                    LocalDate today = LocalDate.now();
                    List<LichHoc> lichHocHomNay = thoiKhoaBieu.timLichHocTheoNgay(today);
                    if (lichHocHomNay.isEmpty()) {
                        System.out.println("Hôm nay (" + today.format(DATE_FORMATTER) + ") không có lịch học.");
                    } else {
                        System.out.println("Lịch học hôm nay (" + today.format(DATE_FORMATTER) + "):");
                        lichHocHomNay.forEach(lichHoc -> System.out.println("\t" + lichHoc));
                    }
                    break;
                case 6:
                    // Xem lịch học tuần này
                    int currentWeek = thoiKhoaBieu.getCurrentWeek(LocalDate.now());
                    if (currentWeek < 1 || currentWeek > 20) {
                        System.out.println("Tuần hiện tại không nằm trong phạm vi học kỳ (1-20).");
                    } else {
                        Tuan tuanHienTai = thoiKhoaBieu.timLichHocTheoTuan(currentWeek);
                        if (tuanHienTai == null || tuanHienTai.getDanhSachThu().stream().allMatch(th -> th.getDanhSachLichHoc().isEmpty())) {
                            System.out.println("Không có lịch học trong tuần này (Tuần " + currentWeek + ").");
                        } else {
                            System.out.println("Lịch học tuần này (Tuần " + currentWeek + "):");
                            System.out.println(tuanHienTai);
                        }
                    }
                    break;
                case 7:
                    // Xem lịch học một ngày cụ thể
                    System.out.print("Nhập ngày (dd/MM/yyyy, ví dụ: 16/05/2025): ");
                    String ngayInput = scanner.nextLine();
                    LocalDate selectedDate;
                    try {
                        selectedDate = LocalDate.parse(ngayInput, DATE_FORMATTER);
                    } catch (DateTimeParseException e) {
                        System.out.println("Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy.");
                        continue;
                    }
                    List<LichHoc> lichHocNgayChon = thoiKhoaBieu.timLichHocTheoNgay(selectedDate);
                    if (lichHocNgayChon.isEmpty()) {
                        System.out.println("Ngày " + selectedDate.format(DATE_FORMATTER) + " không có lịch học.");
                    } else {
                        System.out.println("Lịch học ngày " + selectedDate.format(DATE_FORMATTER) + ":");
                        lichHocNgayChon.forEach(lichHoc -> System.out.println("\t" + lichHoc));
                    }
                    break;
                case 8:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}