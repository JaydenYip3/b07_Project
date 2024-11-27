package com.b07.planetze.util.iterator;

import androidx.annotation.NonNull;

import java.util.Iterator;

public class ZipIterator<L, R> implements Iterator<ZipItem<L, R>> {
    @NonNull private final Iterator<L> left;
    @NonNull private final Iterator<R> right;

    public ZipIterator(@NonNull Iterator<L> left, @NonNull Iterator<R> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean hasNext() {
        return left.hasNext() && right.hasNext();
    }

    @NonNull
    @Override
    public ZipItem<L, R> next() {
        return new ZipItem<>(left.next(), right.next());
    }
}
