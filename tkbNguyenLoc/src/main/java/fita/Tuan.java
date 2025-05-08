package fita;

import java.util.HashMap;
import java.util.Map;

public class Tuan {
    private Map<Integer, Thu> dsThu = new HashMap<>();

    public Thu getThu(int thu) {
        return dsThu.computeIfAbsent(thu, k -> new Thu());
    }
}

