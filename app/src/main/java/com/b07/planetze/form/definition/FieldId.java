package com.b07.planetze.form.definition;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.form.Form;

/**
 * Identifies a field associated with a {@link Form}. <br>
 * Use {@link FormBuilder} instead of creating these manually.
 * @param formId the id of the associated {@link Form}
 * @param index the index of the field
 * @param <T> the type of the field's value
 */
public record FieldId<T>(FormId formId, int index) implements Parcelable {
    private FieldId(@NonNull Parcel in) {
        this(
                in.readParcelable(FormId.class.getClassLoader(), FormId.class),
                in.readInt()
        );
    }

    public static final Creator<FieldId<?>> CREATOR = new Creator<>() {
        @Override
        @NonNull
        public FieldId<?> createFromParcel(Parcel in) {
            return new FieldId<>(in);
        }

        @Override
        @NonNull
        public FieldId<?>[] newArray(int size) {
            return new FieldId[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(formId, flags);
        dest.writeInt(index);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("FieldId[formId=%s, index=%s]",
                formId.toString(),
                index);
    }
}
