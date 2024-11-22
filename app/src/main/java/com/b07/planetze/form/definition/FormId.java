package com.b07.planetze.form.definition;

import androidx.annotation.Nullable;

import com.b07.planetze.form.exception.FormIdException;

/**
 * Holds the id of a form. <br>
 * Forms with different fields must have different ids.
 */
public final class FormId {
    private final int id;
    private static int counter;

    static {
        counter = 0;
    }

    public FormId() {
        id = counter++;
    }

    /**
     * Throws a {@link FormIdException} if this id is not equal to a given id.
     * @param other the given id
     */
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
