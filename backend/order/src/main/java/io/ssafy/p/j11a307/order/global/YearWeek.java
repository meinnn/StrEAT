package io.ssafy.p.j11a307.order.global;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.IsoFields;

public class YearWeek implements Comparable<YearWeek> {
    private final int year;
    private final int week;

    public YearWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public static YearWeek from(LocalDate date) {
        return new YearWeek(date.get(IsoFields.WEEK_BASED_YEAR), date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
    }

    @Override
    public String toString() {
        return String.format("%d-W%02d", year, week);
    }

    @Override
    public int compareTo(YearWeek other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        }
        return Integer.compare(this.week, other.week);
    }

    // equals, hashCode, getters 등 필요에 따라 추가
}
