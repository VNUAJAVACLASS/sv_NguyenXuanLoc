package fita;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CtrinhChinh {
    private static final LocalDate NGAY_BAT_DAU = LocalDate.of(2025, 1, 13);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int SO_TUAN_TOI_DA = 22;
    private Map<Integer, Tuan> dsTuanHoc;

    public CtrinhChinh() {
        this.dsTuanHoc = new HashMap<>();
    }

    public void themLichHoc(LichHoc lichHoc) {
        String thoiGianHoc = lichHoc.getThoiGianHoc();
        List<Integer> weeksAdded = new ArrayList<>();
        for (int i = 0; i < thoiGianHoc.length(); i++) {
            if (thoiGianHoc.charAt(i) != '-') {
                int tuanNum = i + 1;
                weeksAdded.add(tuanNum);
                Tuan tuan = dsTuanHoc.computeIfAbsent(tuanNum, k -> new Tuan(tuanNum));
                Thu thuHoc = tuan.getDsThu().stream()
                        .filter(t -> t.getThu() == lichHoc.getThu())
                        .findFirst()
                        .orElseGet(() -> {
                            Thu newThu = new Thu(lichHoc.getThu());
                            tuan.themThu(newThu);
                            return newThu;
                        });
                thuHoc.themLichHoc(lichHoc);
            }
        }
        if (weeksAdded.isEmpty()) {
            System.err.println("Cảnh báo: LichHoc " + lichHoc.getTenMonHoc() + " không có tuần học hợp lệ: " + thoiGianHoc);
        } else {
            System.out.println("Thêm LichHoc " + lichHoc.getTenMonHoc() + " cho các tuần: " + weeksAdded);
        }
    }

    public void hienThiMenuVaXuLyLuaChon(Scanner scanner) {
        while (true) {
            System.out.println("\n=== MENU THỜI KHÓA BIỂU ===");
            System.out.println("1. Xem thời khóa biểu ngày hiện tại");
            System.out.println("2. Xem thời khóa biểu cả tuần");
            System.out.println("3. Xem thời khóa biểu theo tuần, thứ");
            System.out.println("4. Xem thời khóa biểu theo ngày");
            System.out.println("5. Thoát");
            System.out.print("Chọn tính năng (1-5): ");

            int luaChon;
            try {
                luaChon = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số từ 1 đến 5!");
                continue;
            }

            if (luaChon == 5) {
                System.out.println("Tạm biệt!");
                break;
            }

            switch (luaChon) {
                case 1:
                    xemThoiKhoaBieuNgayHienTai();
                    break;
                case 2:
                    xemThoiKhoaBieuCaTuan(scanner);
                    break;
                case 3:
                    xemThoiKhoaBieuTheoTuanThu(scanner);
                    break;
                case 4:
                    xemThoiKhoaBieuTheoNgay(scanner);
                    break;
                default:
                    System.out.println("Tính năng không hợp lệ! Vui lòng chọn từ 1 đến 5.");
            }

            System.out.print("Nhấn Enter để tiếp tục...");
            scanner.nextLine();
        }
    }

    private void xemThoiKhoaBieuNgayHienTai() {
        LocalDate ngayHienTai = LocalDate.now();
        int tuan = tinhTuanTuNgay(ngayHienTai);
        if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
            System.out.println("Không có lịch học trong tuần này.");
            return;
        }

        int thu = ngayHienTai.getDayOfWeek().getValue();
        int thuHTML = thu == 7 ? 8 : thu + 1;
        String tenThu = thu == 7 ? "Chủ Nhật" : "Thứ " + (thu + 1);
        System.out.println("Thời khóa biểu ngày " + ngayHienTai.format(DATE_FORMATTER) +
                " (Tuần " + tuan + ", " + tenThu + "):");
        hienThiLichHocTheoTuanThu(tuan, thuHTML);
    }

    private void xemThoiKhoaBieuCaTuan(Scanner scanner) {
        System.out.print("Nhập số tuần (1-22): ");
        int tuan;
        try {
            tuan = Integer.parseInt(scanner.nextLine());
            if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
                System.out.println("Tuần phải từ 1 đến " + SO_TUAN_TOI_DA + "!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            return;
        }

        LocalDate ngayDauTuan = NGAY_BAT_DAU.plusWeeks(tuan - 1);
        System.out.println("Thời khóa biểu tuần " + tuan + " (từ " + ngayDauTuan.format(DATE_FORMATTER) + "):");
        Tuan tuanHoc = dsTuanHoc.get(tuan);
        if (tuanHoc == null || tuanHoc.getDsThu().isEmpty()) {
            System.out.println("Không có lịch học trong tuần này.");
            return;
        }

        for (Thu thu : tuanHoc.getDsThu()) {
            String tenThu = thu.getThu() == 8 ? "Chủ Nhật" : "Thứ " + thu.getThu();
            System.out.println("- " + tenThu + ":");
            thu.getDsLichHoc().forEach(LichHoc::hienThiLichHoc);
        }
    }

    private void xemThoiKhoaBieuTheoTuanThu(Scanner scanner) {
        System.out.print("Nhập số tuần (1-22): ");
        int tuan;
        try {
            tuan = Integer.parseInt(scanner.nextLine());
            if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
                System.out.println("Tuần phải từ 1 đến " + SO_TUAN_TOI_DA + "!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            return;
        }

        System.out.print("Nhập thứ (2-8, 2=Thứ Hai, 8=Chủ Nhật): ");
        int thu;
        try {
            thu = Integer.parseInt(scanner.nextLine());
            if (thu < 2 || thu > 8 || thu == 7) {
                System.out.println("Thứ phải là 2-6 hoặc 8!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            return;
        }

        LocalDate ngayDauTuan = NGAY_BAT_DAU.plusWeeks(tuan - 1);
        LocalDate ngayXem = ngayDauTuan.plusDays(thu == 8 ? 6 : thu - 2);
        System.out.println("Thời khóa biểu tuần " + tuan + ", thứ " + (thu == 8 ? "Chủ Nhật" : thu) +
                " (" + ngayXem.format(DATE_FORMATTER) + "):");
        hienThiLichHocTheoTuanThu(tuan, thu);
    }

    private void xemThoiKhoaBieuTheoNgay(Scanner scanner) {
        System.out.print("Nhập ngày (DD/MM/YYYY): ");
        String ngayStr = scanner.nextLine();
        LocalDate ngay;
        try {
            ngay = LocalDate.parse(ngayStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Ngày không hợp lệ! Vui lòng nhập theo định dạng DD/MM/YYYY.");
            return;
        }

        int tuan = tinhTuanTuNgay(ngay);
        if (tuan < 1 || tuan > SO_TUAN_TOI_DA) {
            System.out.println("Ngày " + ngay.format(DATE_FORMATTER) + " không thuộc khoảng thời gian có lịch học.");
            return;
        }

        int thu = ngay.getDayOfWeek().getValue();
        int thuHTML = thu == 7 ? 8 : thu + 1;
        String tenThu = thu == 7 ? "Chủ Nhật" : "Thứ " + (thu + 1);
        System.out.println("Thời khóa biểu ngày " + ngay.format(DATE_FORMATTER) +
                " (" + tenThu + ", Tuần " + tuan + "):");
        hienThiLichHocTheoTuanThu(tuan, thuHTML);
    }

    private int tinhTuanTuNgay(LocalDate ngay) {
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(NGAY_BAT_DAU, ngay);
        return (int) (daysBetween / 7) + 1;
    }

    private void hienThiLichHocTheoTuanThu(int tuan, int thu) {
        Tuan tuanHoc = dsTuanHoc.get(tuan);
        if (tuanHoc == null || tuanHoc.getDsThu().isEmpty()) {
            System.out.println("Không có lịch học trong tuần này.");
            return;
        }

        Thu thuHoc = tuanHoc.getDsThu().stream()
                .filter(t -> t.getThu() == thu)
                .findFirst()
                .orElse(null);

        if (thuHoc == null || thuHoc.getDsLichHoc().isEmpty()) {
            System.out.println("Không có lịch học trong ngày này.");
            return;
        }

        thuHoc.getDsLichHoc().forEach(LichHoc::hienThiLichHoc);
    }

    public Map<Integer, Tuan> getDsTuanHoc() {
        return dsTuanHoc;
    }

    public void setDsTuanHoc(Map<Integer, Tuan> dsTuanHoc) {
        this.dsTuanHoc = dsTuanHoc;
    }
}