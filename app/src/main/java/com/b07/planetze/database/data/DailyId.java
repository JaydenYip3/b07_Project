package com.b07.planetze.database.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Uniquely identifies a logged daily activity stored in a database.
 */
public final class DailyId implements Comparable<DailyId>, Parcelable {
    @NonNull private final String id;
    public DailyId(@NonNull String id) {
        this.id = id;
    }

    public static final Parcelable.Creator<DailyId> CREATOR
            = new Parcelable.Creator<>() {
        public DailyId createFromParcel(Parcel in) {
            return new DailyId(Objects.requireNonNull(in.readString()));
        }
        public DailyId[] newArray(int size) {
            return new DailyId[size];
        }
    };

    @NonNull
    public String get() {
        return id;
    }

    @Override
    public int compareTo(@NonNull DailyId o) {
        return id.compareTo(o.id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("DailyId[id=%s]", id);
    }
}
