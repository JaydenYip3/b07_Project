package com.b07.planetze.util.enumeration;

import androidx.annotation.NonNull;

import java.util.Iterator;

public class Enumerator<T> implements Iterator<EnumeratedItem<T>> {
    @NonNull
    private final Iterator<T> iterator;
    private int index;

    public Enumerator(@NonNull Iterator<T> iterator) {
        this.iterator = iterator;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @NonNull
    @Override
    public EnumeratedItem<T> next() {
        return new EnumeratedItem<>(index++, iterator.next());
    }
}