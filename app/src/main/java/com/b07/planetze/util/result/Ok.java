package com.b07.planetze.util.result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Stores a value for use upon success.
 *
 * @param <T> the type of the {@link Ok} variant
 * @param <E> the type of the {@link Error} variant
 */
public final class Ok<T, E> extends Result<T, E> {
    @NonNull private final T value;

    /**
     * Creates a successful {@link Result}.
     *
     * @param value a value for use upon success
     */
    public Ok(@NonNull T value) {
        this.value = value;
    }

    /**
     * Gets the value stored by this result.
     *
     * @return the value stored by this result
     */
    @NonNull
    public T get() {
        return value;
    }

    @Override
    public Ok<T, E> apply(@NonNull Consumer<T> f) {
        f.accept(value);
        return this;
    }

    @Override
    public Ok<T, E> applyError(@NonNull Consumer<E> f) {
        return this;
    }

    @NonNull
    @Override
    public <U> Ok<U, E> map(@NonNull Function<T, U> f) {
        return new Ok<>(f.apply(value));
    }

    @NonNull
    @Override
    public <F> Ok<T, F> mapError(@NonNull Function<E, F> f) {
        return new Ok<>(value);
    }

    @NonNull
    @Override
    public T getOr(@NonNull T defaultValue) {
        return value;
    }

    @NonNull
    @Override
    public T getOrElse(@NonNull Supplier<T> supplier) {
        return value;
    }

    @NonNull
    @Override
    public T getOrThrowError() {
        return value;
    }

    @NonNull
    @Override
    public Option<T> ok() {
        return new Some<>(value);
    }

    @NonNull
    @Override
    public Option<E> error() {
        return new None<>();
    }

    @Override
    public boolean isOkAnd(@NonNull Predicate<T> predicate) {
        return predicate.test(value);
    }

    @Override
    public boolean isErrorAnd(@NonNull Predicate<E> predicate) {
        return false;
    }

    @Override
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        ok.accept(value);
    }

    @Override
    public <R> R resolve(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        return ok.apply(value);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof Ok<?, ?> ok) && value.equals(ok.value);
    }

    @NonNull
    @Override
    public Ok<T, E> copy() {
        if (value instanceof MutableWithCopy<?> v) {
            @SuppressWarnings("unchecked")
            T copied = (T) v.copy();

            return new Ok<>(copied);
        }
        return new Ok<>(value);
    }

    @NonNull
    @Override
    public Ok<T, E> self() {
        return this;
    }
}
