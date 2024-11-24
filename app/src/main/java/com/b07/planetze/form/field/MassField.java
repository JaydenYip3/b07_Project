package com.b07.planetze.form.field;

import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.result.Result.ok;

import androidx.annotation.NonNull;

import com.b07.planetze.common.measurement.ImmutableMass;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.immutability.ImmutableCopy;
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
}
