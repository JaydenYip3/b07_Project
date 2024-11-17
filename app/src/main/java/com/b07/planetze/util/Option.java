package com.b07.planetze.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Holds an optional value—useful for code explicitness and
 * for avoiding <code>null</code>. <br>
 * Structurally, this is a sum type of variants {@link Option.Some} and
 * {@link Option.None}, representing the presence and absence of the value,
 * respectively.
 * @param <T> the type of the value
 */
public sealed class Option<T> {
    private Option() {}

    /**
     * Creates an {@link Option} from a nullable value.
     * @param value the value
     * @return {@link Option.Some} if the value is non-null;
     *         {@link Option.None} otherwise.
     * @param <T> the type of the value
     */
    @NonNull
    public static <T> Option<T> fromNullable(@Nullable T value) {
        if (value == null) {
            return new None<>();
        }
        return new Some<>(value);
    }

    /**
     * Depending on whether this result is of variant {@link Option.Some}
     * or {@link Option.None}, this method calls 1 of 2 functions with the
     * stored value (if present).
     * @param some the function to call if <code>this</code> is {@link Option.Some}
     * @param none the function to call if <code>this</code> is {@link Option.None}
     */
    public void match(@NonNull Consumer<T> some, @NonNull Runnable none) {
        if (this instanceof Some<T> r) {
            some.accept(r.get());
        } else {
            none.run();
        }
    }

    /**
     * Depending on whether this result is of variant {@link Option.Some}
     * or {@link Option.None}, this method calls 1 of 2 functions with the
     * stored value (if present).
     * @param some the function to call if <code>this</code> is {@link Option.Some}
     * @param none the function to call if <code>this</code> is {@link Option.None}
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public <R> R match(@NonNull Function<T, R> some, @NonNull Supplier<R> none) {
        if (this instanceof Some<T> r) {
            return some.apply(r.get());
        }
        return none.get();
    }

    /**
     * Represents the presence of a value.
     * @param <T> the type of the value
     */
    public static final class Some<T> extends Option<T> {
        private T value;

        private Some() {}

        /**
         * Creates a {@link Option} with a present value.
         * @param value the value
         */
        public Some(@NonNull T value) {
            this.value = value;
        }

        /**
         * Gets the value stored in this {@link Option}.
         * @return the value
         */
        @NonNull
        public T get() {
            return value;
        }
    }

    /**
     * Represents the absence of a value.
     * @param <T> the type of the value
     */
    public static final class None<T> extends Option<T> {
        /**
         * Creates a {@link Option} with an absent value.
         */
        public None() {}
    }
}
