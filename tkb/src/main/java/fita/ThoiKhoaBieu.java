package fita;

import java.util.ArrayList;
import java.util.List;

public class ThoiKhoaBieu {
    private List<Tuan> danhSachTuan; // Danh sách các tuần

    // Constructor
    public ThoiKhoaBieu() {
        this.danhSachTuan = new ArrayList<>();
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
        // Giả định thoiGianHoc có độ dài cố định, mỗi vị trí tương ứng với một tuần (1-20)
        for (int i = 0; i < thoiGianHoc.length() && i < 20; i++) {
            if (Character.isDigit(thoiGianHoc.charAt(i))) {
                // Vị trí i tương ứng với tuần i+1
                int soTuan = i + 1;
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
}