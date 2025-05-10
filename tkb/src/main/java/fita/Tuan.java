package fita;

import java.util.ArrayList;
import java.util.List;

public class Tuan {
    private int soTuan; // Số tuần (1, 2, 3, ...)
    private List<Thu> danhSachThu; // Danh sách các thứ trong tuần

    // Constructor
    public Tuan(int soTuan) {
        this.soTuan = soTuan;
        this.danhSachThu = new ArrayList<>();
        // Khởi tạo các thứ từ Thứ 2 đến Thứ 7
        for (int i = 2; i <= 7; i++) {
            danhSachThu.add(new Thu(String.valueOf(i)));
        }
    }

    // Thêm lịch học vào thứ tương ứng
    public void themLichHoc(LichHoc lichHoc) {
        for (Thu thu : danhSachThu) {
            if (thu.getTenThu().equals(lichHoc.getThu())) {
                thu.themLichHoc(lichHoc);
                break;
            }
        }
    }

    // Getters
    public int getSoTuan() { return soTuan; }
    public List<Thu> getDanhSachThu() { return danhSachThu; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tuần " + soTuan + ":\n");
        for (Thu thu : danhSachThu) {
            if (!thu.getDanhSachLichHoc().isEmpty()) {
                sb.append(thu.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}