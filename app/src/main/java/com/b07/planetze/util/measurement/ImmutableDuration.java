package com.b07.planetze.util.measurement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Duration}. <br>
 * Consider instantiating this with {@link Duration#immutableCopy()}.
 */
public final class ImmutableDuration extends ImmutableCopy<Duration>
        implements ToJson, Parcelable {
    public ImmutableDuration(@NonNull Duration object) {
        super(object);
    }

    /**
     * {@return this duration in hours}
     */
    public double h() {
        return object.h();
    }

    /**
     * {@return this duration in seconds}
     */
    public double s() {
        return object.s();
    }

    @NonNull
    public String format() {
        return object.format();
    }

    @NonNull
    public static ImmutableDuration fromJson(@NonNull Object o) {
        return Duration.fromJson(o).immutableCopy();
    }

    @NonNull
    @Override
    public Object toJson() {
        return object.toJson();
    }

    public static final Parcelable.Creator<ImmutableDuration> CREATOR
            = new Parcelable.Creator<>() {
        public ImmutableDuration createFromParcel(Parcel in) {
            return Duration.CREATOR.createFromParcel(in).immutableCopy();
        }

        public ImmutableDuration[] newArray(int size) {
            return new ImmutableDuration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(object, 0);
    }
}
