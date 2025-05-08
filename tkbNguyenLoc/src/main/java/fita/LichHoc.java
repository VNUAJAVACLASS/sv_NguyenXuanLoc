package fita;

public class LichHoc {
    private String mon;
    private String phong;
    private String nhomTo;
    private String giangVien;
    private int[] tietBatDauKetThuc;

    public LichHoc(String mon, String phong, String nhomTo, String giangVien, int[] tietBatDauKetThuc) {
        this.mon = mon;
        this.phong = phong;
        this.nhomTo = nhomTo;
        this.giangVien = giangVien;
        this.tietBatDauKetThuc = tietBatDauKetThuc;
    }

    public String toString() {
        return String.format("%s | %s | %s | %s | Tiết %d-%d", 
            mon, phong, nhomTo, giangVien, tietBatDauKetThuc[0], tietBatDauKetThuc[1]);
    }
}

