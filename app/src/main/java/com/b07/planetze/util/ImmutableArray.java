package com.b07.planetze.util;

import androidx.annotation.NonNull;

import java.util.Iterator;

/**
 * An array wrapper that provides only accessors.
 * @param <T> the type of each element
 */
public class ImmutableArray<T> implements Iterable<T> {
    @NonNull private final T[] array;

    public ImmutableArray(@NonNull T[] array) {
        this.array = array;
    }

    /**
     * Gets an element via index. <br>
     * Throws {@link IndexOutOfBoundsException}.
     * @param index the index
     * @return <code>array[index]</code>
     */
    @NonNull
    public T get(int index) {
        return array[index];
    }

    /**
     * Gets an element via index, or {@link None} if the index is out of bounds.
     * @param index the index
     * @return the element or {@link None}
     */
    @NonNull
    public Option<T> getOption(int index) {
        try {
            return new Some<>(array[index]);
        } catch (IndexOutOfBoundsException e) {
            return new None<>();
        }
    }

    /**
     * Gets the size of the array.
     * @return the size of the array
     */
    public int size() {
        return array.length;
    }

    /**
     * Checks whether an index is within array bounds.
     * @param index the index
     * @return <code>true</code> iff the index is within array bounds
     */
    public boolean containsIndex(int index) {
        return 0 <= index && index < size();
    }

    private static class ImmutableArrayIterator<T> implements Iterator<T> {
        private ImmutableArray<T> array;
        private int index;
        private ImmutableArrayIterator() {}
        public ImmutableArrayIterator(ImmutableArray<T> array) {
            this.array = array;
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < array.size();
        }

        @Override
        public T next() {
            return array.get(index++);
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new ImmutableArrayIterator<>(this);
    }
}
