package fita;

import java.util.ArrayList;
import java.util.List;

public class Thu {
    private String tenThu; // Tên thứ (Thứ 2, Thứ 3, ...)
    private List<LichHoc> danhSachLichHoc; // Danh sách lịch học trong ngày

    //Hàm khởi tạo
    public Thu(String tenThu) {
        this.tenThu = tenThu;
        this.danhSachLichHoc = new ArrayList<>();
    }

    //Thêm lịch học vào thứ
    public void themLichHoc(LichHoc lichHoc) {
        danhSachLichHoc.add(lichHoc);
    }

    //Get
    public String getTenThu() {
    	return tenThu; 
    }
    
    public List<LichHoc> getDanhSachLichHoc() {
    	return danhSachLichHoc; 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Thứ " + tenThu + ":\n");
        for (LichHoc lich : danhSachLichHoc) {
            sb.append("\t").append(lich.toString()).append("\n");
        }
        return sb.toString();
    }
}