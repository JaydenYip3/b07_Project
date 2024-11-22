package com.b07.planetze.form.definition;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.form.exception.FormIdException;

/**
 * Holds the id of a form. <br>
 * Forms with different fields must have different ids.
 */
public final class FormId implements Parcelable {
    private final int id;
    private static int counter;

    static {
        counter = 0;
    }

    public FormId() {
        id = counter++;
    }

    private FormId(@NonNull Parcel in) {
        id = in.readInt();
    }

    public static final Creator<FormId> CREATOR = new Creator<>() {
        @Override
        @NonNull
        public FormId createFromParcel(@NonNull Parcel in) {
            return new FormId(in);
        }

        @Override
        @NonNull
        public FormId[] newArray(int size) {
            return new FormId[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof FormId other) && id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    @NonNull
    public String toString() {
        return "FormId[" + id + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
    }
}
