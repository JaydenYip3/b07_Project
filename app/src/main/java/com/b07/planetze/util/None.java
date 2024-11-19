package com.b07.planetze.util;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents the absence of an optional value.
 * @param <T> the type of the value
 */
public final class None<T> extends Option<T> {
    /**
     * Creates an {@link Option} with an absent value.
     */
    public None() {}

    @Override
    public void apply(@NonNull Consumer<T> f) {}

    @NonNull
    @Override
    public <U> Option<U> map(@NonNull Function<T, U> f) {
        return new None<>();
    }

    @NonNull
    @Override
    public Option<T> or(@NonNull Option<T> other) {
        return other;
    }

    @NonNull
    @Override
    public Option<T> or(@NonNull Supplier<Option<T>> supplier) {
        return supplier.get();
    }

    @NonNull
    @Override
    public T getOr(@NonNull T defaultValue) {
        return defaultValue;
    }

    @NonNull
    @Override
    public T getOr(@NonNull Supplier<T> supplier) {
        return supplier.get();
    }

    @NonNull
    @Override
    public <E> Result<T, E> okOr(@NonNull E error) {
        return new Error<>(error);
    }

    @NonNull
    @Override
    public <E> Result<T, E> okOr(@NonNull Supplier<E> supplier) {
        return new Error<>(supplier.get());
    }

    @Override
    public boolean isSomeAnd(@NonNull Predicate<T> predicate) {
        return false;
    }

    @Override
    public void match(@NonNull Consumer<T> some, @NonNull Runnable none) {
        none.run();
    }

    @Override
    public <R> R match(@NonNull Function<T, R> some, @NonNull Supplier<R> none) {
        return none.get();
    }
}
