package io.ssafy.p.j11a307.order.global;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.IsoFields;
@Getter
public class YearWeek implements Comparable<YearWeek> {
    private YearMonth yearMonth;
    private int week;
//    private final int year;
//    private final int week;

//    public YearWeek(int year, int week) {
//        this.year = year;
//        this.week = week;
//    }
    public YearWeek(YearMonth yearMonth, int week) {
        this.yearMonth = yearMonth;
        this.week = week;
    }

//    public static YearWeek from(LocalDate date) {
//        return new YearWeek(date.get(IsoFields.WEEK_BASED_YEAR), date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
//    }



    @Override
    public int compareTo(YearWeek other) {
        int compareYearMonth = this.yearMonth.compareTo(other.yearMonth);
        if (compareYearMonth == 0) {
            return Integer.compare(this.week, other.week);
        }
        return compareYearMonth;
    }

    @Override
    public String toString() {
        return yearMonth.getMonth().getValue() + "월 " + week + "주";
    }

}
