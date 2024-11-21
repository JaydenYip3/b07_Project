package com.b07.planetze.form;

import androidx.annotation.Nullable;

import com.b07.planetze.form.exception.FormIdException;

public final class FormId {
    private final int id;
    private static int counter;

    static {
        counter = 0;
    }

    public FormId() {
        id = counter++;
    }

    public void assertEquals(FormId other) {
        if (id != other.id) {
            throw new FormIdException();
        }
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof FormId other) && id == other.id;
    }
}
