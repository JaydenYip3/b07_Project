package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import androidx.annotation.NonNull;

import com.b07.planetze.common.measurement.Distance;
import com.b07.planetze.common.measurement.ImmutableDistance;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.immutability.ImmutableCopy;
import com.b07.planetze.util.result.Result;

public class DistanceField implements Field<ImmutableDistance> {
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
}
