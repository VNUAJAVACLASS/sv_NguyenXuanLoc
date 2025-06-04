package fita;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ThoiKhoaBieu {
    private List<Tuan> danhSachTuan;
    private final LocalDate semesterStartDate;
    private final int currentWeek;

    public ThoiKhoaBieu(int index) {
        this.currentWeek = index >= 0 && index < 23 ? index + 1 : 1; // Mặc định tuần 1 nếu index không hợp lệ
        this.danhSachTuan = new ArrayList<>();
        this.semesterStartDate = calculateSemesterStartDate(this.currentWeek - 1);
        System.out.println("Ngày bắt đầu học kỳ: " + semesterStartDate);
        System.out.println("Ngày bắt đầu tuần hiện tại (tuần " + currentWeek + "): " + calculateCurrentWeekStartDate(this.currentWeek - 1));
    }

    private LocalDate calculateSemesterStartDate(int index) {
        LocalDate today = LocalDate.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        LocalDate mondayOfCurrentWeek = today.minusDays(dayOfWeek - 1); // Thứ Hai của tuần hiện tại
        return mondayOfCurrentWeek.minusDays(index * 7); // Ngày bắt đầu học kỳ
    }

    private LocalDate calculateCurrentWeekStartDate(int index) {
        return semesterStartDate.plusDays(index * 7);
    }

    public LocalDate getSemesterStartDate() {
        return semesterStartDate;
    }

    public LocalDate getCurrentWeekStartDate() {
        return calculateCurrentWeekStartDate(currentWeek - 1);
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    private Tuan getOrCreateTuan(int soTuan) {
        if (soTuan < 1 || soTuan > 23) {
            System.err.println("Số tuần không hợp lệ: " + soTuan);
            return null;
        }
        for (Tuan tuan : danhSachTuan) {
            if (tuan.getSoTuan() == soTuan) {
                return tuan;
            }
        }
        Tuan tuanMoi = new Tuan(soTuan);
        danhSachTuan.add(tuanMoi);
        return tuanMoi;
    }

    public void themLichHoc(LichHoc lichHoc) {
        String thoiGianHoc = lichHoc.getThoiGianHoc();
        if (thoiGianHoc == null || thoiGianHoc.isEmpty()) {
            System.err.println("thoiGianHoc trống cho môn: " + lichHoc.getTenMonHoc());
            return;
        }
        if (thoiGianHoc.length() < 20) {
            thoiGianHoc = String.format("%-" + 20 + "s", thoiGianHoc).replace(' ', '-');
        }
        for (int i = 0; i < thoiGianHoc.length() && i < 20; i++) {
            char c = thoiGianHoc.charAt(i);
            if (c == '-') continue;
            if (!Character.isDigit(c)) {
                System.err.println("Ký tự không hợp lệ tại vị trí " + i + ": " + c + " (môn: " + lichHoc.getTenMonHoc() + ")");
                continue;
            }
            if (c == '1' || (c == '0' && i == 19) || (c == '7' && i == 16) || (c == '8' && i == 17) || (c == '9' && i == 18)) {
                Tuan tuan = getOrCreateTuan(i + 1);
                if (tuan != null) {
                    tuan.themLichHoc(lichHoc);
                }
            }
        }
    }

    public void hienThiThoiKhoaBieu() {
        if (danhSachTuan.isEmpty()) {
            System.out.println("Thời khóa biểu trống.");
            return;
        }
        for (Tuan tuan : danhSachTuan) {
            System.out.println(tuan);
        }
    }

    public List<LichHoc> timLichHocTheoMon(String tenMonHoc) {
        List<LichHoc> ketQua = new ArrayList<>();
        for (Tuan tuan : danhSachTuan) {
            for (Thu thu : tuan.getDanhSachThu()) {
                for (LichHoc lich : thu.getDanhSachLichHoc()) {
                    if (lich.getTenMonHoc().equalsIgnoreCase(tenMonHoc)) {
                        ketQua.add(lich);
                    }
                }
            }
        }
        return ketQua;
    }

    public List<LichHoc> timLichHocTheoThu(String thu) {
        List<LichHoc> ketQua = new ArrayList<>();
        for (Tuan tuan : danhSachTuan) {
            for (Thu t : tuan.getDanhSachThu()) {
                if (t.getTenThu().equalsIgnoreCase(thu)) {
                    ketQua.addAll(t.getDanhSachLichHoc());
                }
            }
        }
        return ketQua;
    }

    public Tuan timLichHocTheoTuan(int soTuan) {
        for (Tuan tuan : danhSachTuan) {
            if (tuan.getSoTuan() == soTuan) {
                return tuan;
            }
        }
        System.out.println("Không có lịch học trong tuần " + soTuan);
        return null;
    }

    public List<LichHoc> timLichHocTheoNgay(LocalDate date) {
        List<LichHoc> ketQua = new ArrayList<>();
        int dayOfWeek = date.getDayOfWeek().getValue();
        if (dayOfWeek == 7) { // Chủ nhật không có lịch
            return ketQua;
        }
        String thu = String.valueOf(dayOfWeek + 1);
        long daysSinceStart = ChronoUnit.DAYS.between(semesterStartDate, date);
        int weekNumber = (int) (daysSinceStart / 7) + 1;
        if (weekNumber < 1 || weekNumber > 20) {
            return ketQua;
        }
        Tuan tuan = timLichHocTheoTuan(weekNumber);
        if (tuan == null) {
            return ketQua;
        }
        for (Thu t : tuan.getDanhSachThu()) {
            if (t.getTenThu().equals(thu)) {
                ketQua.addAll(t.getDanhSachLichHoc());
            }
        }
        return ketQua;
    }

    public int getCurrentWeek(LocalDate date) {
        long daysSinceStart = ChronoUnit.DAYS.between(semesterStartDate, date);
        int weekNumber = (int) (daysSinceStart / 7) + 1;
        return Math.max(1, Math.min(20, weekNumber));
    }
}