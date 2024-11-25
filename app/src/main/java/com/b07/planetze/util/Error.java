package com.b07.planetze.util;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Stores an error for use upon failure.
 *
 * @param <T> the type of the {@link Ok} variant
 * @param <E> the type of the {@link Error} variant
 */
public final class Error<T, E> extends Result<T, E> {
    private E error;

    private Error() {}

    /**
     * Creates a failed {@link Result}.
     *
     * @param error an error for use upon failure
     */
    public Error(@NonNull E error) {
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
    public void apply(@NonNull Consumer<T> f) {}

    @Override
    public void applyError(@NonNull Consumer<E> f) {
        f.accept(error);
    }

    @NonNull
    @Override
    public <U> Result<U, E> map(@NonNull Function<T, U> f) {
        return new Error<>(error);
    }

    @NonNull
    @Override
    public <F> Result<T, F> mapError(@NonNull Function<E, F> f) {
        return new Error<>(f.apply(error));
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
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        error.accept(this.error);
    }

    @Override
    public <R> R match(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        return error.apply(this.error);
    }
}