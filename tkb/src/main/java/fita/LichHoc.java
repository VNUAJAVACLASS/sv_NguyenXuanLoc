package fita;

public class LichHoc {
    private String maMonHoc; 
    private String tenMonHoc; 
    private String nhomTo; 
    private int soTinChi; 
    private String lop; 
    private String thu;
    private int tietBatDau; 
    private int soTiet; 
    private String phong;
    private String giangVien; 
    private String thoiGianHoc;

    // Hàm khởi tạo
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

    // Get Set
    public String getMaMonHoc() {
    	return maMonHoc; 
    }
    
    public String getTenMonHoc() {
    	return tenMonHoc; 
    }
    
    public String getNhomTo() {
    	return nhomTo; 
    }
    
    public int getSoTinChi() {
    	return soTinChi; 
    }
    
    public String getLop() {
    	return lop; 
    }
    
    public String getThu() {
    	return thu; 
    }
    
    public int getTietBatDau() {
    	return tietBatDau; 
    }
    
    public int getSoTiet() {
    	return soTiet; 
    }
    
    public String getPhong() {
    	return phong; 
    }
    
    public String getGiangVien() {
    	return giangVien; 
    }
    
    public String getThoiGianHoc() {
    	return thoiGianHoc; 
    }

    @Override
    public String toString() {
        return String.format("Môn: %s (%s), Nhóm: %s, Tín chỉ: %d, Lớp: %s, Thứ: %s, Tiết: %d-%d, Phòng: %s, Giảng viên: %s, Tuần: %s",
                tenMonHoc, maMonHoc, nhomTo, soTinChi, lop, thu, tietBatDau, tietBatDau + soTiet - 1, phong, giangVien, thoiGianHoc);
    }
}