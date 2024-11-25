package com.b07.planetze.util;

import androidx.annotation.NonNull;

import com.b07.planetze.common.InvalidDateIntervalException;

import java.time.LocalDate;

/**
 * An interval between two dates.
 * @param start the start of this interval
 * @param end the end of this interval (excluded)
 */
public record DateInterval(@NonNull LocalDate start, @NonNull LocalDate end) {
    /**
     * Creates a {@link DateInterval} from <code>start</code>
     * up to but not including <code>end</code>. <br>
     * Throws an {@link InvalidDateIntervalException}
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
