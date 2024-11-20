package com.b07.planetze.util.result;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.MutableWithCopy;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Stores an error for use upon failure.
 *
 * @param <T> the type of the {@link Ok} variant
 * @param <E> the type of the {@link Err} variant
 */
public final class Err<T, E> extends Result<T, E> {
    @NonNull private final E error;

    /**
     * Creates a failed {@link Result}.
     *
     * @param error an error for use upon failure
     */
    public Err(@NonNull E error) {
        this.error = error;
    }

    /**
     * Gets the error stored by this result.
     *
     * @return the error stored by this result
     */
    @NonNull
    public E error() {
        return error;
    }

    @Override
    public Err<T, E> apply(@NonNull Consumer<T> f) {
        return this;
    }

    @Override
    public Err<T, E> applyError(@NonNull Consumer<E> f) {
        f.accept(error);
        return this;
    }

    @NonNull
    @Override
    public <U> Err<U, E> map(@NonNull Function<T, U> f) {
        return new Err<>(error);
    }

    @NonNull
    @Override
    public <F> Err<T, F> mapErr(@NonNull Function<E, F> f) {
        return new Err<>(f.apply(error));
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

    @Override
    public boolean isOkAnd(@NonNull Predicate<T> predicate) {
        return false;
    }

    @Override
    public boolean isErrAnd(@NonNull Predicate<E> predicate) {
        return predicate.test(error);
    }

    @Override
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        error.accept(this.error);
    }

    @Override
    public <R> R match(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        return error.apply(this.error);
    }

    @NonNull
    @Override
    public Err<T, E> copy() {
        if (error instanceof MutableWithCopy<?> e) {
            @SuppressWarnings("unchecked")
            E copied = (E) e.copy();

            return new Err<>(copied);
        }
        return new Err<>(error);
    }
}
