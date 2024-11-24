package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import androidx.annotation.NonNull;

import com.b07.planetze.common.measurement.Duration;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.immutability.ImmutableCopy;
import com.b07.planetze.util.result.Result;

public class DurationField implements Field<ImmutableCopy<Duration>> {
    @NonNull private static final DurationField INSTANCE = new DurationField();
    private DurationField() {}

    @NonNull
    public static DurationField create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Result<Unit, String> validate(
            @NonNull ImmutableCopy<Duration> value) {
        return value.copy().compareTo(Duration.zero()) >= 0
                ? ok() : error("Value must be non-negative");
    }

    @NonNull
    @Override
    public DurationFragment createFragment(@NonNull FieldId<?> fieldId) {
        return DurationFragment.newInstance(fieldId);
    }
}
