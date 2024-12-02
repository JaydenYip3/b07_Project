package com.b07.planetze.util.iterator;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class Enumerable<T> implements Iterable<EnumeratedItem<T>> {
    @NonNull private final Iterable<T> iterable;

    public Enumerable(@NonNull Iterable<T> iterable) {
        this.iterable = iterable;
    }

    @NonNull
    @Override
    public Iterator<EnumeratedItem<T>> iterator() {
        return new Enumerator<>(iterable.iterator());
    }

    public void forEach(@NonNull BiConsumer<Integer, T> f) {
        for (EnumeratedItem<T> next : this) {
            f.accept(next.index(), next.value());
        }
    }
}
