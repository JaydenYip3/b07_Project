package com.b07.planetze.util;

/**
 * A sum type of variants <code>Ok</code> and <code>Error</code>
 * representing either success or failure, respectively.
 * @param <T> the type of the value stored by the <code>Ok</code> variant
 * @param <E> the type of the error stored by the <code>Error</code> variant
 */
public sealed class Result<T, E> {
    private Result() {}

    /**
     * Stores a value for use upon success.
     * @param <T> the type of the <code>Ok</code> variant
     * @param <E> the type of the <code>Error</code> variant
     */
    public static final class Ok<T, E> extends Result<T, E> {
        T value;
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
        E error;

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
