package fita;

public class LichHoc {
    private String maMonHoc; // Mã môn học
    private String tenMonHoc; // Tên môn học
    private String nhomTo; // Nhóm tổ
    private int soTinChi; // Số tín chỉ
    private String lop; // Lớp
    private String thu; // Thứ (2, 3, 4, ...)
    private int tietBatDau; // Tiết bắt đầu
    private int soTiet; // Số tiết
    private String phong; // Phòng học
    private String giangVien; // Giảng viên
    private String thoiGianHoc; // Thời gian học (chuỗi mã hóa tuần)

    // Constructor
    public LichHoc(String maMonHoc, String tenMonHoc, String nhomTo, int soTinChi, String lop,
                   String thu, int tietBatDau, int soTiet, String phong, String giangVien, String thoiGianHoc) {
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

    // Getters and Setters
    public String getMaMonHoc() { return maMonHoc; }
    public String getTenMonHoc() { return tenMonHoc; }
    public String getNhomTo() { return nhomTo; }
    public int getSoTinChi() { return soTinChi; }
    public String getLop() { return lop; }
    public String getThu() { return thu; }
    public int getTietBatDau() { return tietBatDau; }
    public int getSoTiet() { return soTiet; }
    public String getPhong() { return phong; }
    public String getGiangVien() { return giangVien; }
    public String getThoiGianHoc() { return thoiGianHoc; }

    @Override
    public String toString() {
        return String.format("Môn: %s (%s), Nhóm: %s, Tín chỉ: %d, Lớp: %s, Thứ: %s, Tiết: %d-%d, Phòng: %s, Giảng viên: %s, Tuần: %s",
                tenMonHoc, maMonHoc, nhomTo, soTinChi, lop, thu, tietBatDau, tietBatDau + soTiet - 1, phong, giangVien, thoiGianHoc);
    }
}