package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FieldDeparcelizer;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

/**
 * Holds a positive integer.
 */
public final class IntField implements Field<Integer> {
    @NonNull private static final IntField INSTANCE = new IntField();

    private IntField() {}

    public static IntField create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        return value > 0 ? ok() : error("Value must be positive");
    }

    @NonNull
    @Override
    public IntFragment createFragment(@NonNull FieldId<?> fieldId) {
        return IntFragment.newInstance(fieldId);
    }

    @NonNull
    @Override
    public FieldDeparcelizer deparcelizer() {
        return FieldDeparcelizer.INT;
    }

    public static final Parcelable.Creator<IntField> CREATOR
            = new Parcelable.Creator<>() {
        public IntField createFromParcel(Parcel in) {
            return INSTANCE;
        }

        public IntField[] newArray(int size) {
            return new IntField[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {}
}
