package fita;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LichHoc {
    private String maMonHoc;
    private String tenMonHoc;
    private String nhomTo;
    private int soTinChi;
    private String lop;
    private int thu;
    private int tietBatDau;
    private int soTiet;
    private String phong;
    private String giangVien;
    private String thoiGianHoc;

    private static final LocalDate NGAY_BAT_DAU = LocalDate.of(2025, 1, 13);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LichHoc(String maMonHoc, String tenMonHoc, String nhomTo, int soTinChi, String lop, int thu, int tietBatDau,
                   int soTiet, String phong, String giangVien, String thoiGianHoc) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.nhomTo = nhomTo;
        this.soTinChi = soTinChi;
        this.lop = lop;
        this.thu = thu;
        this.tietBatDau = tietBatDau;
        this.soTiet = soTiet;
        this.phong = phong;
        this.giangVien = giangVien;
        this.thoiGianHoc = thoiGianHoc;
    }

    public void hienThiLichHoc() {
        String tenThu = thu == 8 ? "Chủ Nhật" : "Thứ " + thu;
        String formattedLine = String.format(
                "┃ %-20s | %-6s | Tiết %-5s | %-8s | %-10s | %-30s ┃",
                tenMonHoc, tenThu, tietBatDau + "-" + (tietBatDau + soTiet - 1),
                phong, giangVien, getThoiGianHocHienThi());
        System.out.println(formattedLine);
    }

    public int getTuanDauTien() {
        for (int i = 0; i < thoiGianHoc.length(); i++) {
            if (thoiGianHoc.charAt(i) != '-') {
                return i + 1;
            }
        }
        return -1;
    }

    public int getTuanCuoiCung() {
        for (int i = thoiGianHoc.length() - 1; i >= 0; i--) {
            if (thoiGianHoc.charAt(i) != '-') {
                return i + 1;
            }
        }
        return -1;
    }

    private LocalDate tinhNgayTuTuan(int tuan) {
        return NGAY_BAT_DAU.plusWeeks(tuan - 1);
    }

    public String getThoiGianHocHienThi() {
        int tuanDau = getTuanDauTien();
        int tuanCuoi = getTuanCuoiCung();
        if (tuanDau == -1 || tuanCuoi == -1) {
            return "Không có lịch học";
        }
        LocalDate ngayDau = tinhNgayTuTuan(tuanDau);
        LocalDate ngayCuoi = tinhNgayTuTuan(tuanCuoi);
        return String.format("Tuần %d (%s) - Tuần %d (%s)", tuanDau, ngayDau.format(DATE_FORMATTER),
                tuanCuoi, ngayCuoi.format(DATE_FORMATTER));
    }

    public String getMaMonHoc() { return maMonHoc; }
    public void setMaMonHoc(String maMonHoc) { this.maMonHoc = maMonHoc; }
    public String getTenMonHoc() { return tenMonHoc; }
    public void setTenMonHoc(String tenMonHoc) { this.tenMonHoc = tenMonHoc; }
    public String getNhomTo() { return nhomTo; }
    public void setNhomTo(String nhomTo) { this.nhomTo = nhomTo; }
    public int getSoTinChi() { return soTinChi; }
    public void setSoTinChi(int soTinChi) { this.soTinChi = soTinChi; }
    public String getLop() { return lop; }
    public void setLop(String lop) { this.lop = lop; }
    public int getThu() { return thu; }
    public void setThu(int thu) { this.thu = thu; }
    public int getTietBatDau() { return tietBatDau; }
    public void setTietBatDau(int tietBatDau) { this.tietBatDau = tietBatDau; }
    public int getSoTiet() { return soTiet; }
    public void setSoTiet(int soTiet) { this.soTiet = soTiet; }
    public String getPhong() { return phong; }
    public void setPhong(String phong) { this.phong = phong; }
    public String getGiangVien() { return giangVien; }
    public void setGiangVien(String giangVien) { this.giangVien = giangVien; }
    public String getThoiGianHoc() { return thoiGianHoc; }
    public void setThoiGianHoc(String thoiGianHoc) { this.thoiGianHoc = thoiGianHoc; }
}