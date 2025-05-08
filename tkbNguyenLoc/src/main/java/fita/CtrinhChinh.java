package fita;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class CtrinhChinh {
    private Map<Integer, Tuan> dsTuan = new HashMap<>();
    private static final LocalDate NGAY_BAT_DAU = LocalDate.of(2025, 1, 13);

    public Tuan getTuan(int tuan) {
        return dsTuan.computeIfAbsent(tuan, k -> new Tuan());
    }

    public void themLich(int tuan, int thu, LichHoc lh) {
        getTuan(tuan).getThu(thu).themLichHoc(lh);
    }

    public LocalDate quyDoiNgay(int tuan, int thu) {
        return NGAY_BAT_DAU.plusDays((tuan - 1) * 7 + (thu - 2));
    }

    public int quyDoiTuan(LocalDate ngay) {
        long days = ChronoUnit.DAYS.between(NGAY_BAT_DAU, ngay);
        return (int)(days / 7) + 1;
    }

    public int quyDoiThu(LocalDate ngay) {
        return ngay.getDayOfWeek().getValue() + 1; // Monday = 2
    }
}
