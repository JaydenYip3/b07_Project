package com.b07.planetze.form;

import androidx.annotation.Nullable;

public final class FormId {
    private final int id;
    private static int counter;

    static {
        counter = 0;
    }

    public FormId() {
        id = counter++;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof FormId other) && id == other.id;
    }
}
