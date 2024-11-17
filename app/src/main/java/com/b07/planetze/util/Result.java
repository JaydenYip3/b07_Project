package com.b07.planetze.util;

import androidx.annotation.NonNull;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a success xor a failure. <br>
 * This allows for error handling in asynchronous contexts. <br>
 * This also provides a higher level of abstraction if used as an alternative
 * to checked exceptions. <br>
 * Structurally, this is a sum type of variants {@link Result.Ok} (success) and
 * {@link Result.Error} (failure), with each variant holding a value.
 * @param <T> the type of the value stored by the {@link Result.Ok} variant
 * @param <E> the type of the error stored by the {@link Result.Error} variant
 */
public sealed class Result<T, E> {
    private Result() {}

    /**
     * Depending on whether this result is of variant {@link Result.Ok}
     * or {@link Result.Error}, this method calls 1 of 2 functions with the
     * associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Result.Ok}
     * @param error the function to call if <code>this</code> is
     *              {@link Result.Error}
     */
    public void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error) {
        if (this instanceof Ok<T, E> r) {
            ok.accept(r.get());
        } else {
            error.accept(((Error<T, E>) this).error());
        }
    }

    /**
     * Depending on whether this result is of variant {@link Result.Ok}
     * or {@link Result.Error}, this method calls 1 of 2 functions with the
     * associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Result.Ok}
     * @param error the function to call if <code>this</code> is
     *              {@link Result.Error}
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public <R> R match(@NonNull Function<T, R> ok, @NonNull Function<E, R> error) {
        if (this instanceof Ok<T, E> r) {
            return ok.apply(r.get());
        }
        return error.apply(((Error<T, E>) this).error());
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
         * Gets the error stored by this result.
         * @return the error stored by this result
         */
        @NonNull
        public E error() {
            return error;
        }
    }
}
