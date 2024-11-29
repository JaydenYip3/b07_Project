package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.definition.FieldDeparcelizer;
import com.b07.planetze.util.measurement.ImmutableMass;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

public final class MassField implements Field<ImmutableMass> {
    @NonNull private static final MassField INSTANCE = new MassField();
    private MassField() {}

    @NonNull
    public static MassField create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(
            @NonNull ImmutableMass value) {
        return value.kg() >= 0 ? ok() : error("Value must be non-negative");
    }

    @NonNull
    @Override
    public MassFragment createFragment(@NonNull FieldId<?> fieldId) {
        return MassFragment.newInstance(fieldId);
    }

    @NonNull
    @Override
    public FieldDeparcelizer deparcelizer() {
        return FieldDeparcelizer.MASS;
    }

    public static final Parcelable.Creator<MassField> CREATOR
            = new Parcelable.Creator<>() {
        public MassField createFromParcel(Parcel in) {
            return INSTANCE;
        }

        public MassField[] newArray(int size) {
            return new MassField[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {}
}
