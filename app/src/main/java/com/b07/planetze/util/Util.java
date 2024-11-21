package com.b07.planetze.util;

import androidx.annotation.NonNull;

import com.b07.planetze.util.iterator.Enumerable;
import com.b07.planetze.util.iterator.ZipIterable;

public final class Util {
    private Util() {}

    @NonNull
    public static <T> Enumerable<T> enumerate(@NonNull Iterable<T> iterable) {
        return new Enumerable<>(iterable);
    }

    @NonNull
    public static <L, R> ZipIterable<L, R> zip(@NonNull Iterable<L> left, @NonNull Iterable<R> right) {
        return new ZipIterable<>(left, right);
    }
}
