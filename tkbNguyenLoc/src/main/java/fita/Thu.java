package fita;

import java.util.ArrayList;
import java.util.List;

public class Thu {
    private List<LichHoc> dsLichHoc = new ArrayList<>();

    public void themLichHoc(LichHoc lh) {
        dsLichHoc.add(lh);
    }

    public List<LichHoc> getLichHoc() {
        return dsLichHoc;
    }
}
