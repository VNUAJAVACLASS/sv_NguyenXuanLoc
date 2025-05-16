package fita;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ThoiKhoaBieu {
    private List<Tuan> danhSachTuan; // Danh sách các tuần
    private final LocalDate semesterStartDate; // Ngày bắt đầu học kỳ

    // Constructor
    public ThoiKhoaBieu() {
        this.danhSachTuan = new ArrayList<>();
        this.semesterStartDate = LocalDate.of(2025, 1, 13); // Cố định ngày bắt đầu học kỳ: 13/01/2025
    }

    // Thêm tuần mới nếu chưa tồn tại
    private Tuan getOrCreateTuan(int soTuan) {
        for (Tuan tuan : danhSachTuan) {
            if (tuan.getSoTuan() == soTuan) {
                return tuan;
            }
        }
        Tuan tuanMoi = new Tuan(soTuan);
        danhSachTuan.add(tuanMoi);
        return tuanMoi;
    }

    // Thêm lịch học vào thời khóa biểu
    public void themLichHoc(LichHoc lichHoc) {
        String thoiGianHoc = lichHoc.getThoiGianHoc();
        // Kiểm tra thoiGianHoc có hợp lệ không
        if (thoiGianHoc == null || thoiGianHoc.isEmpty()) {
            return; // Bỏ qua nếu thời gian học không hợp lệ
        }

        // Duyệt qua chuỗi thoiGianHoc, mỗi số tại vị trí i tương ứng với tuần i+1
        for (int i = 0; i < thoiGianHoc.length() && i < 20; i++) {
            char c = thoiGianHoc.charAt(i);
            // Nếu ký tự là số (tức là tuần có lịch học)
            if (Character.isDigit(c) && c != '0') { // Bỏ qua số 0 vì nó không đại diện cho tuần hợp lệ
                int soTuan = i + 1; // Tuần bắt đầu từ 1
                Tuan tuan = getOrCreateTuan(soTuan);
                tuan.themLichHoc(lichHoc);
            }
        }
    }

    // Hiển thị toàn bộ thời khóa biểu
    public void hienThiThoiKhoaBieu() {
        if (danhSachTuan.isEmpty()) {
            System.out.println("Thời khóa biểu trống.");
            return;
        }
        for (Tuan tuan : danhSachTuan) {
            System.out.println(tuan);
        }
    }

    // Tìm lịch học theo môn học
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

    // Tìm lịch học theo thứ
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

    // Tìm lịch học theo tuần
    public Tuan timLichHocTheoTuan(int soTuan) {
        for (Tuan tuan : danhSachTuan) {
            if (tuan.getSoTuan() == soTuan) {
                return tuan;
            }
        }
        return null; // Trả về null nếu không tìm thấy tuần
    }

    // Tìm lịch học theo ngày cụ thể
    public List<LichHoc> timLichHocTheoNgay(LocalDate date) {
        List<LichHoc> ketQua = new ArrayList<>();
        // Xác định thứ (2=Thứ Hai, 3=Thứ Ba, ..., 7=Thứ Bảy, 1=Chủ Nhật)
        int dayOfWeek = date.getDayOfWeek().getValue(); // 1=Thứ Hai, ..., 7=Chủ Nhật
        if (dayOfWeek == 7) return ketQua; // Không có lịch học vào Chủ Nhật
        String thu = String.valueOf(dayOfWeek + 1); // Chuyển sang định dạng 2-7

        // Tính số tuần
        long daysSinceStart = java.time.temporal.ChronoUnit.DAYS.between(semesterStartDate, date);
        int weekNumber = (int) (daysSinceStart / 7) + 1;
        if (weekNumber < 1 || weekNumber > 20) return ketQua; // Ngoài phạm vi học kỳ

        // Lấy lịch học cho thứ và tuần tương ứng
        Tuan tuan = timLichHocTheoTuan(weekNumber);
        if (tuan != null) {
            for (Thu t : tuan.getDanhSachThu()) {
                if (t.getTenThu().equals(thu)) {
                    ketQua.addAll(t.getDanhSachLichHoc());
                }
            }
        }
        return ketQua;
    }

    // Tính số tuần cho ngày hiện tại
    public int getCurrentWeek(LocalDate date) {
        long daysSinceStart = java.time.temporal.ChronoUnit.DAYS.between(semesterStartDate, date);
        int weekNumber = (int) (daysSinceStart / 7) + 1;
        return Math.max(1, Math.min(20, weekNumber)); // Giới hạn trong 1-20
    }
}