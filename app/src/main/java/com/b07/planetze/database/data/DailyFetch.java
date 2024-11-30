package com.b07.planetze.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * A {@link Daily} with an added id and date.
 */
public record DailyFetch(
        @NonNull DailyId id,
        @NonNull DailyData data
) implements Parcelable {
    @NonNull public static final Comparator<DailyFetch> descendingEmissions
            = (a, b) -> b.emissions().total().compareTo(a.emissions().total());

    @NonNull
    public DailyFetch withReplacedDaily(@NonNull Daily daily) {
        return new DailyFetch(id, new DailyData(data.date(), daily));
    }

    @NonNull
    public Emissions emissions() {
        return data.daily().emissions();
    }

    @NonNull
    public LocalDate date() {
        return data.date();
    }

    @NonNull
    public String summary() {
        return data.daily().summary();
    }

    @NonNull
    public Daily daily() {
        return data.daily();
    }

    @NonNull
    public DailyType type() {
        return data.daily().type();
    }

    public static final Parcelable.Creator<DailyFetch> CREATOR
            = new Parcelable.Creator<>() {
        public DailyFetch createFromParcel(Parcel in) {
            return new DailyFetch(
                    DailyId.CREATOR.createFromParcel(in),
                    DailyData.CREATOR.createFromParcel(in)
            );
        }

        public DailyFetch[] newArray(int size) {
            return new DailyFetch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(id, 0);
        dest.writeParcelable(data, 0);
    }
}
