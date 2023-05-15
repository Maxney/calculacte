package com.example.calculacte.data;

import java.time.Month;
import java.util.List;
import java.util.Map;


public class Holidays {
    /**
     * 1, 2, 3, 4, 5, 6, 7, 8 января — Новогодние каникулы;
     * 7 января — Рождество Христово;
     * 23 февраля — День защитника Отечества;
     * 8 марта — Международный женский день;
     * 1 мая — Праздник Весны и Труда;
     * 9 мая — День Победы;
     * 12 июня — День России;
     * 4 ноября — День народного единства.
     *
     * @return map key=месяца , value=праздниные дни
     */
    public static Map<Month, List<Integer>> getHolidayDaysList() {
        return Map.of(Month.JANUARY, List.of(1, 2, 3, 4, 5, 6, 7, 8),
                Month.FEBRUARY, List.of(23),
                Month.MARCH, List.of(8),
                Month.MAY, List.of(1, 9),
                Month.JUNE, List.of(12),
                Month.NOVEMBER, List.of(4));
    }
}
