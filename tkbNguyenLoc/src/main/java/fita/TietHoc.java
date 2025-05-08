package fita;

public enum TietHoc {
    TIET_1("06:45"), TIET_2("07:35"), TIET_3("08:25"),
    TIET_4("09:15"), TIET_5("10:05"), TIET_6("10:55"),
    TIET_7("12:30"), TIET_8("13:20"), TIET_9("14:10"),
    TIET_10("15:00"), TIET_11("15:50"), TIET_12("16:40"),
    TIET_13("17:30"), TIET_14("18:20"), TIET_15("19:10"),
    TIET_16("20:00");

    private final String gioBatDau;
    TietHoc(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }
    public String getGioBatDau() {
        return gioBatDau;
    }
}

