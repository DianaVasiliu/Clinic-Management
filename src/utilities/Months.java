package utilities;

import java.util.HashMap;
import java.util.Map;

public enum Months {
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private final int number;
    private static final Map<Integer, Months> map;
    static {
        map = new HashMap<>();
        for (Months month : Months.values()) {
            map.put(month.number, month);
        }
    }

    Months(int number) {
        this.number = number;
    }

    public static int getNumber(Months month){
        if (month != null) {
            return month.number;
        }
        else {
            System.err.println(Errors.INVALID_MONTH);
            return 0;
        }
    }

    public static String getMonth(int number) {
        return map.get(number).toString();
    }
}