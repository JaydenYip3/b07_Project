package com.b07.planetze.util;

import androidx.annotation.NonNull;

import com.b07.planetze.util.enumeration.Enumerable;

public final class Util {
    @NonNull
    public static <T> Enumerable<T> enumerate(@NonNull Iterable<T> iterable) {
        return new Enumerable<>(iterable);
    }
}
