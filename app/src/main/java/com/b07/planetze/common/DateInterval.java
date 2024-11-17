package com.b07.planetze.common;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public record DateInterval(@NonNull LocalDate start, @NonNull LocalDate end) {
    /**
     * Creates a <code>DateInterval</code> from <code>start</code>
     * up to but not including <code>end</code>. <br>
     * Throws a <code>InvalidDateIntervalException</code>
     * if <code>end</code> < <code>start</code>.
     * @param start the start of this interval
     * @param end the end of this interval (excluded)
     */
    public DateInterval {
        if (end.isBefore(start)) {
            throw new InvalidDateIntervalException("End of interval before start");
        }
    }

    /**
     * Determines whether a date is in this interval.
     * @param date the date to check
     * @return <code>true</code> iff this interval contains the date.
     */
    public boolean contains(@NonNull LocalDate date) {
        return !start.isAfter(date) && date.isBefore(end);
    }
}
