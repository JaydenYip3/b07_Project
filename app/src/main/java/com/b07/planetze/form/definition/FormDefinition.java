package com.b07.planetze.form.definition;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;
import com.b07.planetze.form.exception.FormIdException;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.option.Option;

import java.util.Objects;

/**
 * Describes the fields of a {@link Form} (i.e., stores the name, initial value,
 * and validators of all fields). <br>
 * This is used for creating forms. <br>
 * Use {@link FormBuilder} instead of creating this manually. <br>
 * @param id the form's id
 * @param fields the form's fields
 */
public record FormDefinition(
        @NonNull FormId id,
        @NonNull ImmutableList<FieldDefinition<?>> fields
) implements Parcelable {
    public boolean containsField(@NonNull FieldId<?> field) {
        return id.equals(field.formId());
    }

    /**
     * Throws a {@link FormIdException} if this definition does not contain a
     * given field
     * @param field the given field
     */
    public void assertContainsField(@NonNull FieldId<?> field) {
        if (!containsField(field)) {
            throw new FormIdException();
        }
    }

    @NonNull
    private <T> FieldDefinition<T> fieldDefinition(@NonNull FieldId<T> field) {
        assertContainsField(field);

        @SuppressWarnings("unchecked")
        FieldDefinition<T> f = (FieldDefinition<T>) fields.get(field.index());

        return f;
    }

    @NonNull
    public <T> Field<T> field(@NonNull FieldId<T> field) {
        return fieldDefinition(field).field();
    }

    @NonNull
    public String name(@NonNull FieldId<?> field) {
        return fieldDefinition(field).name();
    }

    @NonNull
    public <T> Option<T> initialValue(@NonNull FieldId<T> field) {
        return fieldDefinition(field).initialValue();
    }

    /**
     * {@return a new form with <code>this</code> as its definition}
     */
    @NonNull
    public Form createForm() {
        return new Form(this);
    }

    public static final Parcelable.Creator<FormDefinition> CREATOR
            = new Parcelable.Creator<>() {
        public FormDefinition createFromParcel(Parcel in) {
            FormId id = FormId.CREATOR.createFromParcel(in);

            ImmutableList<FieldDefinition<?>> fields = Util
                    .deparcelizeImmutableList(in, p -> {
                String name = Objects.requireNonNull(p.readString());

                FieldDeparcelizer d = FieldDeparcelizer.CREATOR
                        .createFromParcel(p);

                @SuppressWarnings("unchecked")
                Field<Object> field = (Field<Object>)d.deparcelizeField(p);

                var value = Util.deparcelizeOption(p, d::deparcelizeValue);

                return new FieldDefinition<>(name,
                        InitiallyFilled.create(field, value));
            });

            return new FormDefinition(id, fields);
        }

        public FormDefinition[] newArray(int size) {
            return new FormDefinition[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(id, 0);
        Util.parcelizeImmutableList(dest, fields, (p, def) -> {
            p.writeString(def.name());
            p.writeParcelable(def.field().deparcelizer(), 0);
            p.writeParcelable(def.field(), 0);
            Util.parcelizeOption(p, def.initialValue());
        });
    }
}
