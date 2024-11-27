package com.b07.planetze.util.iterator;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class ZipIterable<L, R> implements Iterable<ZipItem<L, R>> {
    @NonNull private final Iterable<L> left;
    @NonNull private final Iterable<R> right;

    public ZipIterable(@NonNull Iterable<L> left, @NonNull Iterable<R> right) {
        this.left = left;
        this.right = right;
    }

    @NonNull
    @Override
    public Iterator<ZipItem<L, R>> iterator() {
        return new ZipIterator<>(left.iterator(), right.iterator());
    }

    public void forEach(@NonNull BiConsumer<L, R> f) {
        for (ZipItem<L, R> next : this) {
            f.accept(next.left(), next.right());
        }
    }
}
