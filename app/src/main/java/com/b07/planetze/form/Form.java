package com.b07.planetze.form;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An editable form.
 * @see FormBuilder
 */
public final class Form implements Parcelable {
    @NonNull private final FormDefinition definition;
    @NonNull private final List<Option<Object>> values;

    /**
     * Instantiates a form given a definition.
     * @param definition the form's definition
     * @see FormBuilder
     */
    public Form(@NonNull FormDefinition definition) {
        this(definition, new ArrayList<>(definition.fields().size()));

        definition
                .fields()
                .forEach(f -> values.add(f.initialValue().map(v -> v)));
    }

    private Form(
            @NonNull FormDefinition definition,
            @NonNull List<Option<Object>> values
    ) {
        this.definition = definition;
        this.values = values;
    }

    public FormDefinition definition() {
        return definition;
    }

    /**
     * Sets the value of a field. <br>
     * Sets the field to {@link None} If the provided value is invalid. <br>
     * Throws {@link com.b07.planetze.form.exception.FormIdException} if the
     * given field id is not attached to this form.
     * @param field the field's id
     * @param value the value to set
     * @return an error message (if the provided value is invalid)
     * @param <T> the type of the field's value
     */
    @NonNull
    public <T> Result<Unit, String> set(
            @NonNull FieldId<T> field,
            @NonNull T value
    ) {
        // this calls assertContainsField
        Result<Unit, String> r = definition.field(field).validate(value);

        values.set(field.index(), r.getOption().map(x -> value));
        return r;
    }

    /**
     * {@return the value of a field} <br>
     * Throws {@link com.b07.planetze.form.exception.FormIdException} if the
     * given field id is not attached to this form. <br>
     * Use {@link Form#submit()} if you require all fields to be filled.
     * @param field the field to get
     * @param <T> the type of the field's value
     */
    @NonNull
    public <T> Option<T> get(@NonNull FieldId<T> field) {
        definition.assertContainsField(field);

        @SuppressWarnings("unchecked")
        Option<T> value = values.get(field.index()).map(v -> (T) v);

        return value;
    }

    /**
     * {@return a form submission if all fields were set, or the indices of the
     *          missing fields otherwise.}
     */
    @NonNull
    public Result<FormSubmission, Set<Integer>> submit() {
        Set<Integer> missing = new HashSet<>();
        List<Object> presentValues = new ArrayList<>();

        Util.enumerate(values).forEach(
                (i, v) -> v.match(presentValues::add, () -> missing.add(i)));

        if (!missing.isEmpty()) {
            return error(missing);
        }
        return ok(new FormSubmission(
                definition.id(),
                new ImmutableList<>(presentValues)
        ));
    }

    public static final Parcelable.Creator<Form> CREATOR
            = new Parcelable.Creator<>() {
        public Form createFromParcel(Parcel in) {
            FormDefinition def = FormDefinition.CREATOR.createFromParcel(in);
            List<Option<Object>> list = new ArrayList<>();

            def.fields().forEach(field -> {
                list.add(Util.deparcelizeOption(
                        in, field.field().deparcelizer()::deparcelizeValue));
            });
            return new Form(def, list);
        }

        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(definition, 0);
        values.forEach(v -> Util.parcelizeOption(dest, v));
    }
}
