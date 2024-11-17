package com.b07.planetze.type;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A sum type of variants {@link Result.Ok} and {@link Result.Error}
 * representing success and failure, respectively. <br>
 * Each of these variants holds a value.
 * @param <T> the type of the value stored by the {@link Result.Ok} variant
 * @param <E> the type of the error stored by the {@link Result.Error} variant
 */
public sealed class Result<T, E> {
    private Result() {}

    /**
     * Calls <code>ok</code> with the value stored by the {@link Result.Ok} variant
     * if <code>this</code> is successful; otherwise, calls <code>error</code> with
     * the value stored by the {@link Result.Error} variant.
     * @param ok the function to call on success
     * @param error the function to call on failure
     */
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        if (this instanceof Result.Ok<T, E> r) {
            ok.accept(r.get());
        } else {
            error.accept(((Result.Error<T, E>) this).error());
        }
    }

    /**
     * Calls <code>ok</code> with the value stored by the {@link Result.Ok} variant
     * if this result is successful; otherwise, calls <code>error</code> with
     * the value stored by the {@link Result.Error} variant.
     * @param ok the function to call on success
     * @param error the function to call on failure
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public <R> R match(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        if (this instanceof Result.Ok<T, E> r) {
            return ok.apply(r.get());
        }
        return error.apply(((Result.Error<T, E>) this).error());
    }

    /**
     * Stores a value for use upon success.
     * @param <T> the type of the {@link Result.Ok} variant
     * @param <E> the type of the {@link Result.Error} variant
     */
    public static final class Ok<T, E> extends Result<T, E> {
        private T value;
        private Ok() {}

        /**
         * Creates a successful {@link Result}.
         * @param value a value for use upon success
         */
        public Ok(@NonNull T value) {
            this.value = value;
        }

        /**
         * Gets the value stored by this result.
         * @return the value stored by this result
         */
        @NonNull
        public T get() {
            return value;
        }
    }

    /**
     * Stores an error for use upon failure.
     * @param <T> the type of the {@link Result.Ok} variant
     * @param <E> the type of the {@link Result.Error} variant
     */
    public static final class Error<T, E> extends Result<T, E> {
        private E error;

        private Error() {}

        /**
         * Creates a failed {@link Result}.
         * @param error an error for use upon failure
         */
        public Error(@NonNull E error) {
            this.error = error;
        }

        /**
         * Gets the error associated with this result.
         * @return the error associated with this result
         */
        @NonNull
        public E error() {
            return error;
        }
    }
}
