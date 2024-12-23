package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.definition.FieldDeparcelizer;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

public final class DistanceField implements Field<ImmutableDistance> {
    @NonNull private static final DistanceField INSTANCE = new DistanceField();
    private DistanceField() {}

    @NonNull
    public static DistanceField create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull ImmutableDistance value) {
        return value.m() >= 0 ? ok() : error("Value must be non-negative");
    }

    @NonNull
    @Override
    public DistanceFragment createFragment(@NonNull FieldId<?> fieldId) {
        return DistanceFragment.newInstance(fieldId);
    }

    @NonNull
    @Override
    public FieldDeparcelizer deparcelizer() {
        return FieldDeparcelizer.DISTANCE;
    }

    public static final Parcelable.Creator<DistanceField> CREATOR
            = new Parcelable.Creator<>() {
        public DistanceField createFromParcel(Parcel in) {
            return INSTANCE;
        }

        public DistanceField[] newArray(int size) {
            return new DistanceField[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {}
}
