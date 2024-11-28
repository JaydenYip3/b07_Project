package com.b07.planetze.database.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.option.Option;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Locally stores daily activities.
 */
public final class DailyMap {
    @NonNull private final Map<DailyId, LocalDate> datesById;
    @NonNull private final
    SortedMap<LocalDate, SortedMap<DailyId, Daily>> dailies;

    public DailyMap() {
        datesById = new HashMap<>();
        dailies = new TreeMap<>();
    }

    /**
     * {@return the map of dailies on a specific date, creating one if one doesn't
     *          exist}
     * @param date the date
     */
    @NonNull
    private SortedMap<DailyId, Daily> dailiesOn(@NonNull LocalDate date) {
        return dailies.computeIfAbsent(date, k -> new TreeMap<>());
    }

    // this suppresses an incorrect NullPointerException warning
    @SuppressWarnings("ConstantConditions")
    public void put(@NonNull DailyFetch fetch) {
        Option.mapNull(datesById.put(fetch.id(), fetch.date()))
                .apply(prevDate -> dailies.get(prevDate).remove(fetch.id()));

        dailiesOn(fetch.date()).put(fetch.id(), fetch.daily());
    }

    // this suppresses an incorrect NullPointerException warning
    @SuppressWarnings("ConstantConditions")
    public void remove(@NonNull DailyId id) {
        Option.mapNull(datesById.remove(id))
                .apply(date -> dailies.get(date).remove(id));
    }

    // this suppresses an incorrect NullPointerException warning
    @SuppressWarnings("ConstantConditions")
    @NonNull
    public Option<DailyFetch> get(@NonNull DailyId id) {
        return Option.mapNull(datesById.get(id))
                .map(date -> new DailyFetch(
                        id, date, dailies.get(date).get(id)));
    }

    @NonNull
    public DailyFetchList getAllIn(@NonNull DateInterval itv) {
        List<DailyFetch> list = new ArrayList<>();

        dailies.subMap(itv.start(), itv.end()).forEach((date, map) -> {
            map.forEach((id, daily) -> {
                list.add(new DailyFetch(id, date, daily));
            });
        });

        return new DailyFetchList(new ImmutableList<>(list));
    }

    public boolean isEmpty() {
        return datesById.isEmpty();
    }
}
