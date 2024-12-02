package com.b07.planetze.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ChronoUnit;

/**
 * An interval between two dates.
 */
public final class DateInterval {
    @NonNull private final LocalDate start;
    @NonNull private final LocalDate end;

    public DateInterval(@NonNull LocalDate start, @NonNull LocalDate end) {
        if (end.isBefore(start)) {
            throw new DateIntervalException("End of interval before start");
        }
        this.start = start;
        this.end = end;
    }

    /**
     * {@return a date interval containing all days in a given year}
     * @param year the given year
     */
    @NonNull
    public static DateInterval year(@NonNull Year year) {
        return between(
                year.atMonth(Month.JANUARY).atDay(1),
                year.plusYears(1).atMonth(Month.JANUARY).atDay(1)
        );
    }

    /**
     * {@return a date interval containing all days in a given year-month}
     * @param month the given year-month
     */
    @NonNull
    public static DateInterval month(@NonNull YearMonth month) {
        return between(month.atDay(1), month.plusMonths(1).atDay(1));
    }

    /**
     * {@return a date interval containing all days of the sunday-starting week
     * that contains a given day}
     * @param day the given day
     */
    @NonNull
    public static DateInterval week(@NonNull LocalDate day) {
        return between(
                day.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)),
                day.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        );
    }

    /**
     * {@return a date interval containing a single day}
     * @param day the day
     */
    @NonNull
    public static DateInterval day(@NonNull LocalDate day) {
        return between(day, day.plusDays(1));
    }

    /**
     * {@return a date interval from <code>start</code> to <code>end</code>,
     *          not including <code>end</code>} <br>
     * Use the other factory methods if you need all days in a
     * year/month/week/day.
     * @param start the start date
     * @param end the end date (exclusive)
     */
    @NonNull
    public static DateInterval between(@NonNull LocalDate start,
                                       @NonNull LocalDate end) {
        return new DateInterval(start, end);
    }

    /**
     * {@return <code>true</code> iff this interval contains a given date}
     * @param date the given date
     */
    public boolean contains(@NonNull LocalDate date) {
        return !start.isAfter(date) && date.isBefore(end);
    }

    /**
     * {@return the number of days contained in this interval}
     */
    public long length() {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * {@return the starting date of this interval}
     */
    public LocalDate start() {
        return start;
    }

    /**
     * {@return the day after the last day contained in this interval}
     */
    public LocalDate end() {
        return end;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof DateInterval other)
                && start.equals(other.start)
                && end.equals(other.end);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("[%s, %s)", start, end);
    }
}
