package fita;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        WebScheduleParser webParser = (WebScheduleParser) ScheduleParserFactory.createParser(
                ScheduleParserFactory.ParserType.WEB, null
        );
        List<LichHoc> danhSachLichHoc = webParser.parseSchedule();
        int index = webParser.getActiveIndex();

        int currentWeek = index + 1;
        System.out.println("Index từ hệ thống: " + index + " (Tuần hiện tại: " + currentWeek + ")");

        ThoiKhoaBieu thoiKhoaBieu;
        try {
            thoiKhoaBieu = new ThoiKhoaBieu(index);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
            return;
        }
        for (LichHoc lich : danhSachLichHoc) {
            thoiKhoaBieu.themLichHoc(lich);
        }

        LocalDate today = LocalDate.now();
        List<LichHoc> lichHocHomNay = thoiKhoaBieu.timLichHocTheoNgay(today);
        System.out.println("\n=== LỊCH HỌC HÔM NAY (" + today.format(DATE_FORMATTER) + ") ===");
        if (lichHocHomNay.isEmpty()) {
            System.out.println("Hôm nay không có lịch học.");
        } else {
            lichHocHomNay.forEach(lichHoc -> System.out.println("\t" + lichHoc));
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== QUẢN LÝ THỜI KHÓA BIỂU ===");
            System.out.println("1. Xem lịch học một ngày cụ thể");
            System.out.println("2. Xem lịch học một tuần cụ thể");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
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
                case 2:
                    System.out.print("Nhập số tuần (1-20, ví dụ: 1, 2, ...): ");
                    int soTuan;
                    try {
                        soTuan = scanner.nextInt();
                        scanner.nextLine();
                        if (soTuan < 1 || soTuan > 23) {
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
                        System.out.println("Lịch học tuần " + soTuan + ":");
                        System.out.println(tuan);
                    }
                    break;
                case 3:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}