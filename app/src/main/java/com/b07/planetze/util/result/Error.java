package com.b07.planetze.util.result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.Unit;
import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Stores an error for use upon failure.
 *
 * @param <T> the type of the {@link Ok} variant
 * @param <E> the type of the {@link Error} variant
 */
public final class Error<T, E> extends Result<T, E> {

    // Intentionally package-private
    @NonNull static final Error<?, Unit> UNIT_INSTANCE = new Error<>(Unit.UNIT);

    @NonNull private final E value;

    /**
     * Intentionally package-private; use {@link Result#error}
     */
    Error(@NonNull E value) {
        this.value = value;
    }

    /**
     * Gets the error stored by this result.
     *
     * @return the error stored by this result
     */
    @NonNull
    public E get() {
        return value;
    }

    /**
     * {@return <code>this</code>, but with a different <code>Ok</code>-type}
     * @param <U> the new {@link Ok}-type
     */
    @NonNull
    public <U> Error<U, E> map() {
        @SuppressWarnings("unchecked")
        var e = (Error<U, E>) this;
        return e;
    }

    @NonNull
    @Override
    public Error<T, E> apply(@NonNull Consumer<T> f) {
        return this;
    }

    @NonNull
    @Override
    public Error<T, E> applyError(@NonNull Consumer<E> f) {
        f.accept(value);
        return this;
    }

    @NonNull
    @Override
    public <U> Error<U, E> map(@NonNull Function<T, U> f) {
        return new Error<>(value);
    }

    @NonNull
    @Override
    public <F> Error<T, F> mapError(@NonNull Function<E, F> f) {
        return new Error<>(f.apply(value));
    }

    @NonNull
    @Override
    public T getOr(@NonNull T defaultValue) {
        return defaultValue;
    }

    @NonNull
    @Override
    public T getOrElse(@NonNull Supplier<T> supplier) {
        return supplier.get();
    }

    @NonNull
    @Override
    public T getOrThrowError() {
        if (value instanceof RuntimeException e) {
            throw e;
        }
        throw new ResultException("held error is not a RuntimeException");
    }

    @NonNull
    @Override
    public None<T> getOption() {
        return Option.none();
    }

    @NonNull
    @Override
    public Some<E> getErrorOption() {
        return Option.some(value);
    }

    @Override
    public boolean isOkAnd(@NonNull Predicate<T> predicate) {
        return false;
    }

    @Override
    public boolean isErrorAnd(@NonNull Predicate<E> predicate) {
        return predicate.test(value);
    }

    @Override
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        error.accept(this.value);
    }

    @Override
    public <R> R resolve(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        return error.apply(this.value);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return (o instanceof Error<?, ?> e) && value.equals(e.value);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Error(%s)", value);
    }

    @NonNull
    @Override
    public Error<T, E> copy() {
        if (value instanceof MutableWithCopy<?> e) {
            @SuppressWarnings("unchecked")
            E copied = (E) e.copy();

            return new Error<>(copied);
        }
        return new Error<>(value);
    }

    @NonNull
    @Override
    public Error<T, E> self() {
        return this;
    }
}
