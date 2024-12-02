package com.b07.planetze.form.definition;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.DistanceField;
import com.b07.planetze.form.field.DurationField;
import com.b07.planetze.form.field.IntField;
import com.b07.planetze.form.field.MassField;
import com.b07.planetze.form.field.MoneyField;
import com.b07.planetze.util.measurement.ImmutableDistance;
import com.b07.planetze.util.measurement.ImmutableDuration;
import com.b07.planetze.util.measurement.ImmutableMass;

import java.util.function.Function;

public enum FieldDeparcelizer implements Parcelable {
    INT(x -> IntField.create(),
            Parcel::readInt),
    CHOICE(ChoiceField.CREATOR::createFromParcel,
            Parcel::readInt),
    DISTANCE(x -> DistanceField.create(),
            ImmutableDistance.CREATOR::createFromParcel),
    DURATION(x -> DurationField.create(),
            ImmutableDuration.CREATOR::createFromParcel),
    MASS(x -> MassField.create(),
            ImmutableMass.CREATOR::createFromParcel),
    MONEY(x -> MoneyField.create(),
            Parcel::readDouble);

    @NonNull private final Function<Parcel, Field<?>> fieldFromParcel;

    @NonNull private final Function<Parcel, ?> valueFromParcel;

    FieldDeparcelizer(
            @NonNull Function<Parcel, Field<?>> fieldFromParcel,
            @NonNull Function<Parcel, ?> valueFromParcel
    ) {
        this.fieldFromParcel = fieldFromParcel;
        this.valueFromParcel = valueFromParcel;
    }

    public Field<?> deparcelizeField(@NonNull Parcel in) {
        return this.fieldFromParcel.apply(in);
    }

    public Object deparcelizeValue(@NonNull Parcel in) {
        return this.valueFromParcel.apply(in);
    }

    public static final Parcelable.Creator<FieldDeparcelizer> CREATOR
            = new Parcelable.Creator<>() {
        public FieldDeparcelizer createFromParcel(Parcel in) {
            return valueOf(in.readString());
        }

        public FieldDeparcelizer[] newArray(int size) {
            return new FieldDeparcelizer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name());
    }
}
