package com.b07.planetze.util;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A list wrapper that prevents mutation of the list.
 * @param <T> the type of each element
 */
public class ImmutableList<T> implements Iterable<T> {
    @NonNull private final List<T> list;

    public ImmutableList(@NonNull T[] array) {
        list = Arrays.asList(array);
    }

    public ImmutableList(@NonNull List<T> list) {
        this.list = list;
    }

    /**
     * Gets an element via index. <br>
     * Throws {@link IndexOutOfBoundsException}.
     * @param index the index
     * @return <code>list[index]</code>
     */
    @NonNull
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Gets an element via index, or {@link None} if the index is out of bounds.
     * @param index the index
     * @return the element or {@link None}
     */
    @NonNull
    public Option<T> getOption(int index) {
        try {
            return new Some<>(list.get(index));
        } catch (IndexOutOfBoundsException e) {
            return new None<>();
        }
    }

    /**
     * Gets the size of the list.
     * @return the size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Checks whether an index is within list bounds.
     * @param index the index
     * @return <code>true</code> iff the index is within list bounds
     */
    public boolean containsIndex(int index) {
        return 0 <= index && index < size();
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
