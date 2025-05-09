package fita;

import java.util.ArrayList;
import java.util.List;

public class Tuan {
    private int tuan; // Week number (1-22)
    private List<Thu> dsThu; // List of days in the week

    public Tuan(int tuan) {
        this.tuan = tuan;
        this.dsThu = new ArrayList<>();
    }

    public void themThu(Thu thu) {
        dsThu.add(thu);
    }

    public int getTuan() {
        return tuan;
    }

    public void setTuan(int tuan) {
        this.tuan = tuan;
    }

    public List<Thu> getDsThu() {
        return dsThu;
    }

    public void setDsThu(List<Thu> dsThu) {
        this.dsThu = dsThu;
    }
}