package com.b07.planetze.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.daily.DailyType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record DailySummary(
        @NonNull DailyId id,
        @NonNull LocalDate date,
        @NonNull DailyType type,
        @NonNull Emissions emissions
) implements Parcelable {
    public static final Parcelable.Creator<DailySummary> CREATOR
            = new Parcelable.Creator<>() {
        public DailySummary createFromParcel(Parcel in) {
            return new DailySummary(
                    DailyId.CREATOR.createFromParcel(in),
                    LocalDate.parse(in.readString(),
                            DateTimeFormatter.ISO_LOCAL_DATE),
                    DailyType.CREATOR.createFromParcel(in),
                    Emissions.CREATOR.createFromParcel(in)
            );
        }

        public DailySummary[] newArray(int size) {
            return new DailySummary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(id, 0);
        dest.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        dest.writeParcelable(type, 0);
        dest.writeParcelable(emissions, 0);
    }
}
