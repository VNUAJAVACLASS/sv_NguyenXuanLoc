package fita;

import java.util.ArrayList;
import java.util.List;

public class Thu {
    private int thu; // Day of the week (2=Thứ Hai, 3=Thứ Ba, ..., 8=Chủ Nhật)
    private List<LichHoc> dsLichHoc; // List of schedule entries for the day

    public Thu(int thu) {
        this.thu = thu;
        this.dsLichHoc = new ArrayList<>();
    }

    public void themLichHoc(LichHoc lichHoc) {
        dsLichHoc.add(lichHoc);
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public List<LichHoc> getDsLichHoc() {
        return dsLichHoc;
    }

    public void setDsLichHoc(List<LichHoc> dsLichHoc) {
        this.dsLichHoc = dsLichHoc;
    }
}
