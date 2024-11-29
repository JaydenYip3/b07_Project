package com.b07.planetze.util;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.iterator.Enumerable;
import com.b07.planetze.util.iterator.ZipIterable;
import com.b07.planetze.util.option.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
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

    public static <T extends Parcelable> void parcelizeImmutableList(
            @NonNull ImmutableList<T> list,
            @NonNull Parcel dest
    ) {
        dest.writeInt(list.size());
        list.forEach(v -> dest.writeParcelable(v, 0));
    }

    public static <T> void parcelizeImmutableList(
            @NonNull Parcel dest,
            @NonNull ImmutableList<T> list,
            @NonNull BiConsumer<Parcel, T> writeToParcel
    ) {
        dest.writeInt(list.size());
        list.forEach(v -> writeToParcel.accept(dest, v));
    }

    public static <T> ImmutableList<T> deparcelizeImmutableList(
            @NonNull Parcel in,
            @NonNull Function<Parcel, T> createFromParcel
    ) {
        return new ImmutableList<>(deparcelizeList(in, createFromParcel));
    }

    public static <T> void parcelizeList(
            @NonNull Parcel dest,
            @NonNull List<T> list,
            @NonNull BiConsumer<Parcel, T> writeToParcel
    ) {
        dest.writeInt(list.size());
        list.forEach(v -> writeToParcel.accept(dest, v));
    }

    public static <T> List<T> deparcelizeList(
            @NonNull Parcel in,
            @NonNull Function<Parcel, T> createFromParcel
    ) {
        List<T> list = new ArrayList<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            list.add(createFromParcel.apply(in));
        }
        return list;
    }

    public static void parcelizeOption(
            @NonNull Parcel dest,
            Option<?> option
    ) {
        dest.writeBoolean(option.isSome());
        option.apply(dest::writeValue);
    }

    public static <T> Option<T> deparcelizeOption(
            @NonNull Parcel in,
            @NonNull Function<Parcel, T> createFromParcel
    ) {
        boolean isSome = in.readBoolean();
        return isSome ? some(createFromParcel.apply(in)) : none();
    }
}
