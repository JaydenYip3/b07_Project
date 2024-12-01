package com.b07.planetze.util.measurement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.immutability.ImmutableCopy;

/**
 * Immutably stores a copy of a {@link Mass}. <br>
 * Consider instantiating this with {@link Mass#immutableCopy()}.
 */
public final class ImmutableMass extends ImmutableCopy<Mass>
        implements ToJson, Parcelable {
    public ImmutableMass(@NonNull Mass object) {
        super(object);
    }

    /**
     * {@return this mass in kilograms}
     */
    public double kg() {
        return object.kg();
    }

    /**
     * {@return this mass in grams}
     */
    public double g() {
        return object.g();
    }

    /**
     * {@return this mass in pounds}
     */
    public double lb() {
        return object.lb();
    }

    @NonNull
    public String format() {
        return object.format();
    }

    @NonNull
    public static ImmutableMass fromJson(@NonNull Object o) {
        return Mass.fromJson(o).immutableCopy();
    }

    @NonNull
    @Override
    public Object toJson() {
        return object.toJson();
    }

    public static final Creator<ImmutableMass> CREATOR
            = new Parcelable.Creator<>() {
        public ImmutableMass createFromParcel(Parcel in) {
            return Mass.CREATOR.createFromParcel(in).immutableCopy();
        }

        public ImmutableMass[] newArray(int size) {
            return new ImmutableMass[size];
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
