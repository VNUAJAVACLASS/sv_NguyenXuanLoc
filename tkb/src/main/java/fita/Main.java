package fita;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        // Lấy output từ LayCodeHTML
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream customOut = new PrintStream(outputStream);
        System.setOut(customOut);

        LayCodeHTML layCodeHTML = new LayCodeHTML();
        layCodeHTML.layVaLuuTKBHtml();

        System.setOut(originalOut);
        String consoleOutput = outputStream.toString();
        //System.out.println(consoleOutput);

        // Phân tích index tuần hiện tại
        int index = parseCurrentWeek(consoleOutput);
        
        Scanner scanner = new Scanner(System.in);
        /*
        if (index == -1 || index < 0 || index > 19) {
            System.err.println("Không thể lấy index hợp lệ từ output của LayCodeHTML (index = " + index + ").");
            System.out.print("Nhập index (0-19, ví dụ: 17 cho tuần 18): ");
            try {
                index = scanner.nextInt();
                scanner.nextLine();
                if (index < 0 || index > 19) {
                    System.err.println("Index phải từ 0 đến 19!");
                    return;
                }
            } catch (Exception e) {
                System.err.println("Vui lòng nhập index hợp lệ!");
                return;
            }
        }
        */
        int currentWeek = index + 1;
        System.out.println("Index từ hệ thống: " + index + " (Tuần hiện tại: " + currentWeek + ")");

        // Đọc thời khóa biểu từ file HTML
        DocHTML docHTML = new DocHTML();
        List<LichHoc> danhSachLichHoc = docHTML.docThoiKhoaBieu("src/main/resources/timetable.html");

        // Khởi tạo thời khóa biểu
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

        // Hiển thị lịch học hôm nay ngay khi chạy
        LocalDate today = LocalDate.now();
        List<LichHoc> lichHocHomNay = thoiKhoaBieu.timLichHocTheoNgay(today);
        System.out.println("\n=== LỊCH HỌC HÔM NAY (" + today.format(DATE_FORMATTER) + ") ===");
        if (lichHocHomNay.isEmpty()) {
            System.out.println("Hôm nay không có lịch học.");
        } else {
            lichHocHomNay.forEach(lichHoc -> System.out.println("\t" + lichHoc));
        }

        // Menu để xem thời khóa biểu theo ngày hoặc tuần
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
    
    
    private static int parseCurrentWeek(String consoleOutput) {
        Pattern pattern = Pattern.compile("Phần tử đang chọn là phần tử thứ: (\\d+)");
        Matcher matcher = pattern.matcher(consoleOutput);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi phân tích index: " + e.getMessage());
            }
        }
        return -1;
    }
    
}