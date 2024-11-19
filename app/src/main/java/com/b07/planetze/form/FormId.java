package com.b07.planetze.form;

import androidx.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public final class FormId {
    private final int id;

    public FormId() {
        id = ThreadLocalRandom.current().nextInt();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof FormId other) && id == other.id;
    }
}
