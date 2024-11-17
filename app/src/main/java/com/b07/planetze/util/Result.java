package com.b07.planetze.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A sum type of variants <code>Ok</code> and <code>Error</code>
 * representing either success or failure, respectively.
 * @param <T> the type of the value stored by the <code>Ok</code> variant
 * @param <E> the type of the error stored by the <code>Error</code> variant
 */
public sealed class Result<T, E> {
    private Result() {}

    /**
     * Calls <code>ok</code> with the value stored by the <code>Ok</code> variant
     * if <code>this</code> is successful; otherwise, calls <code>error</code> with
     * the value stored by the <code>Error</code> variant.
     * @param ok the function to call on success
     * @param error the function to call on failure
     */
    public void match(Consumer<T> ok, Consumer<E> error) {
        if (this instanceof Result.Ok<T, E> r) {
            ok.accept(r.get());
        } else {
            error.accept(((Result.Error<T, E>) this).error());
        }
    }

    /**
     * Calls <code>ok</code> with the value stored by the <code>Ok</code> variant
     * if this result is successful; otherwise, calls <code>error</code> with
     * the value stored by the <code>Error</code> variant.
     * @param ok the function to call on success
     * @param error the function to call on failure
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public <R> R match(Function<T, R> ok, Function<E, R> error) {
        if (this instanceof Result.Ok<T, E> r) {
            return ok.apply(r.get());
        }
        return error.apply(((Result.Error<T, E>) this).error());
    }

    /**
     * Stores a value for use upon success.
     * @param <T> the type of the <code>Ok</code> variant
     * @param <E> the type of the <code>Error</code> variant
     */
    public static final class Ok<T, E> extends Result<T, E> {
        private T value;
        private Ok() {}

        /**
         * Creates a successful <code>Result</code>.
         * @param value a value for use upon success
         */
        public Ok(T value) {
            this.value = value;
        }

        /**
         * Gets the value stored by this result.
         * @return the value stored by this result
         */
        public T get() {
            return value;
        }
    }

    /**
     * Stores an error for use upon failure.
     * @param <T> the type of the <code>Ok</code> variant
     * @param <E> the type of the <code>Error</code> variant
     */
    public static final class Error<T, E> extends Result<T, E> {
        private E error;

        private Error() {}

        /**
         * Creates a failed <code>Result</code>.
         * @param error an error for use upon failure
         */
        public Error(E error) {
            this.error = error;
        }

        /**
         * Gets the error associated with this result.
         * @return the error associated with this result
         */
        public E error() {
            return error;
        }
    }
}
