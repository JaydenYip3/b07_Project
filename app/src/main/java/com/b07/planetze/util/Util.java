package com.b07.planetze.util;

import androidx.annotation.NonNull;

import com.b07.planetze.util.iterator.Enumerable;
import com.b07.planetze.util.iterator.ZipIterable;

import java.util.ArrayList;
import java.util.function.Supplier;

public final class Util {
    private Util() {}

    /**
     * Pairs each iteration with an index.
     * @param iterable an {@link Iterable}
     * @param <T> the type of the value being iterated over
     * @return an iterator of pairs (i, i-th value) for all i
     */
    @NonNull
    public static <T> Enumerable<T> enumerate(@NonNull Iterable<T> iterable) {
        return new Enumerable<>(iterable);
    }

    /**
     * Zips two iterators together.
     * @param left an iterable
     * @param right an iterable
     * @return an iterator of pairs (i-th left value, i-th right value)
     *         for all i
     * @param <L> the values of the left iterable
     * @param <R> the values of the right iterable
     */
    @NonNull
    public static <L, R> ZipIterable<L, R> zip(
            @NonNull Iterable<L> left,
            @NonNull Iterable<R> right
    ) {
        return new ZipIterable<>(left, right);
    }

    /**
     * Fills an array with function-returned values.
     * @param array the array
     * @param supplier the function
     * @param <T> the type of the array
     */
    public static <T> void fill(T[] array, Supplier<T> supplier) {
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
    }

    /**
     * {@return a new array list populated by function-returned values}
     * @param size the size of the list
     * @param supplier the function
     * @param <T> the values held by the list
     */
    public static <T> ArrayList<T> filledArrayList(
            int size,
            Supplier<T> supplier
    ) {
        ArrayList<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    public static int toInt(Object o) {
        return ((Number) o).intValue();
    }

    public static double toDouble(Object o) {
        return ((Number) o).doubleValue();
    }
}
