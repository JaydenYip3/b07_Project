package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.Field;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FieldDeparcelizer;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.result.Result;

/**
 * Holds the index (starting from 0) of a single selection of choices.
 */
public final class ChoiceField implements Field<Integer> {
    private final @NonNull ImmutableList<String> choices;

    private ChoiceField(@NonNull ImmutableList<String> choices) {
        this.choices = choices;
    }

    public static ChoiceField withChoices(@NonNull String... choices) {
        return new ChoiceField(new ImmutableList<>(choices));
    }

    public static ChoiceField withChoices(
            @NonNull ImmutableList<String> choices
    ) {
        return new ChoiceField(choices);
    }

    @NonNull
    public ImmutableList<String> choices() {
        return choices;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(@NonNull Integer value) {
        if (choices.containsIndex(value)) {
            return ok(Unit.UNIT);
        }
        return error("Integer index out of bounds");
    }

    @NonNull
    @Override
    public ChoiceFragment createFragment(@NonNull FieldId<?> fieldId) {
        return ChoiceFragment.newInstance(fieldId);
    }

    @NonNull
    @Override
    public FieldDeparcelizer deparcelizer() {
        return FieldDeparcelizer.CHOICE;
    }

    public static final Parcelable.Creator<ChoiceField> CREATOR
            = new Parcelable.Creator<>() {
        public ChoiceField createFromParcel(Parcel in) {
            return new ChoiceField(
                    Util.deparcelizeImmutableList(in, Parcel::readString));
        }

        public ChoiceField[] newArray(int size) {
            return new ChoiceField[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        Util.parcelizeImmutableList(dest, choices, Parcel::writeString);
    }
}
