package com.b07.planetze.database.data;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.util.immutability.ImmutableList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A collection of {@link DailyFetch} <br>
 */
public final class DailyFetchList implements Iterable<DailyFetch> {
    @NonNull private final ImmutableList<DailyFetch> dailies;

    /**
     * @param dailies a list of dailies sorted by date
     */
    public DailyFetchList(@NonNull ImmutableList<DailyFetch> dailies) {
        this.dailies = dailies;
    }

    @NonNull
    public static DailyFetchList empty() {
        return new DailyFetchList(new ImmutableList<>(new ArrayList<>()));
    }

    public int size() {
        return dailies.size();
    }

    /**
     * {@return a new <code>Emissions</code> containing the sum of all
     *          daily activity emissions in this list}
     */
    @NonNull
    public Emissions emissions() {
        Emissions sum = Emissions.zero();
        dailies.forEach(f -> sum.add(f.daily().emissions()));
        return sum;
    }

    @NonNull
    @Override
    public Iterator<DailyFetch> iterator() {
        return dailies.iterator();
    }
}
