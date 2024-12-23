package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.FieldFragment;
import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FieldDeparcelizer;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

/**
 * Holds a dollar amount.
 */
public final class MoneyField implements Field<Double> {
    @NonNull public static final MoneyField INSTANCE = new MoneyField();

    private MoneyField() {}

    @NonNull
    public static MoneyField create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Double value) {
        return value >= 0 ? ok() : error("Value must be positive");
    }

    @NonNull
    @Override
    public FieldFragment<? extends Field<Double>, Double> createFragment(@NonNull FieldId<?> fieldId) {
        return MoneyFragment.newInstance(fieldId);
    }

    @NonNull
    @Override
    public FieldDeparcelizer deparcelizer() {
        return FieldDeparcelizer.MONEY;
    }

    public static final Parcelable.Creator<MoneyField> CREATOR
            = new Parcelable.Creator<>() {
        public MoneyField createFromParcel(Parcel in) {
            return INSTANCE;
        }

        public MoneyField[] newArray(int size) {
            return new MoneyField[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {}
}
