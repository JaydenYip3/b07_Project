package com.b07.planetze.util.option;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.Unit;
import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a value that may be absent. <br>
 * This is useful for code explicitness and for building abstractions around
 * possibly-absent values. <br>
 * Structurally, this is a sum type of variants {@link Some} and {@link None},
 * representing the presence and absence of the value, respectively.
 * @param <T> the type of the value
 */
public sealed abstract class Option<T>
        implements MutableWithCopy<Option<T>>
        permits Some, None {
    /**
     * {@return a new <code>Some</code> containing a value}
     * @param value the value
     * @param <T> the type of the value
     */
    @NonNull
    public static <T> Some<T> some(@NonNull T value) {
        return new Some<>(value);
    }

    /**
     * {@return a new <code>Some(Unit.UNIT)</code>}
     */
    @NonNull
    public static Some<Unit> some() {
        return Some.UNIT_INSTANCE;
    }

    /**
     * {@return an instance of <code>None</code>}
     * @param <T> the type of the <code>Option</code>
     */
    @NonNull
    public static <T> None<T> none() {
        @SuppressWarnings("unchecked")
        None<T> none = (None<T>) None.INSTANCE;

        return none;
    }

    /**
     * Creates an {@link Option} from a nullable value.
     * @param value the value
     * @return a {@link Some} containing the value if it is non-null;
     * {@link None} otherwise.
     * @param <T> the type of the value
     */
    @NonNull
    public static <T> Option<T> mapNull(@Nullable T value) {
        return value == null ? none() : some(value);
    }

    /**
     * Creates an {@link Option} from a nullable {@link Option}.
     * @param value the value
     * @return the value if it is non-null; {@link None} otherwise.
     * @param <T> the inner type of the value
     */
    @NonNull
    public static <T> Option<T> flattenNull(@Nullable Option<T> value) {
        return value == null ? none() : value;
    }

    /**
     * Applies a function to the held value if <code>this</code> is
     * {@link Some}; does nothing otherwise.
     * @param f the function
     * @return <code>this</code>
     */
    @NonNull
    public abstract Option<T> apply(@NonNull Consumer<T> f);

    /**
     * Calls a function if <code>this</code> is {@link None}.
     * @param f the function
     * @return <code>this</code>
     */
    @NonNull
    public abstract Option<T> applyNone(@NonNull Runnable f);

    /**
     * {@return a new <code>Some</code> holding the output of a given function
     * if <code>this</code> is <code>Some</code>—otherwise returns a new
     * <code>None</code>.
     * @param f the given function
     * @return a new {@link Option}
     * @param <U> the return type of the function
     */
    @NonNull
    public abstract <U> Option<U> map(@NonNull Function<T, U> f);

    /**
     * Returns <code>this</code> if <code>this</code> is {@link Some};
     * otherwise, returns a given {@link Option}.
     * @param other the given {@link Option}
     * @return <code>this</code> or <code>other</code>
     */
    @NonNull
    public abstract Option<T> or(@NonNull Option<T> other);

    /**
     * Returns <code>this</code> if <code>this</code> is {@link Some};
     * otherwise, returns the output of a given function.
     * @param supplier the given function
     * @return <code>this</code> or the output of <code>supplier</code>
     */
    @NonNull
    public abstract Option<T> orElse(@NonNull Supplier<Option<T>> supplier);

    /**
     * {@return the held value if <code>this</code> is <code>Some</code>;
     *          otherwise returns a default value}
     * @param defaultValue the default value
     */
    @NonNull
    public abstract T getOr(@NonNull T defaultValue);

    /**
     * {@return the held value if <code>this</code> is <code>Some</code>;
     *          otherwise returns the output of a function}
     * @param supplier the function
     */
    @NonNull
    public abstract T getOrElse(@NonNull Supplier<T> supplier);

    /**
     * {@return the held value if <code>this</code> is <code>Some</code>} <br>
     * Throws a given exception if <code>this</code> is <code>None</code>.
     * @param exception the given exception
     */
    @NonNull
    public abstract T getOrThrow(@NonNull RuntimeException exception);

    /**
     * {@return the held value if <code>this</code> is <code>Some</code>} <br>
     * Throws an exception given by a function if <code>this</code> is
     * <code>None</code>.
     * @param supplier the given function
     */
    @NonNull
    public abstract T getOrThrow(@NonNull Supplier<RuntimeException> supplier);

    /**
     * Creates an {@link Ok} with the held value if <code>this</code> is
     * <code>Some</code>; otherwise, creates a {@link Error} with a given error.
     * @param error the error
     * @return a new {@link Result}
     * @param <E> the type of the error
     */
    @NonNull
    public abstract <E> Result<T, E> okOr(@NonNull E error);

    /**
     * Creates an {@link Ok} with the held value if <code>this</code> is
     * <code>Some</code>; otherwise, creates a {@link Error} with an error given
     * by the output of a function.
     * @param supplier the function
     * @return a new {@link Result}
     * @param <E> the type of the error
     */
    @NonNull
    public abstract <E> Result<T, E> okOr(@NonNull Supplier<E> supplier);

    /**
     * Returns <code>true</code> iff <code>this</code> is {@link Some}. <br>
     * If you require the held value, use
     * <code>if (x instanceof Some&lt;T&gt; some)</code>
     * or {@link Option#resolve}.
     * @return <code>true</code> iff <code>this</code> is {@link Some}.
     */
    public boolean isSome() {
        return this instanceof Some<T>;
    }

    /**
     * Calls a function with the held value if <code>this</code> is
     * {@link Some}.
     * Returns <code>true</code> iff the function is called and outputs
     * <code>true</code>.
     * @param predicate the function
     * @return <code>true</code> iff <code>this</code> is {@link Some} and
     *         <code>predicate(value)</code> returns <code>true</code>.
     */
    public abstract boolean isSomeAnd(@NonNull Predicate<T> predicate);

    /**
     * Returns <code>true</code> iff <code>this</code> is {@link None}.
     * @return <code>true</code> iff <code>this</code> is {@link None}
     */
    public boolean isNone() {
        return this instanceof None<T>;
    }

    /**
     * Depending on whether <code>this</code> is {@link Some}
     * or {@link None}, this method calls 1 of 2 functions with the
     * stored value (if present).
     * @param some the function to call if <code>this</code> is {@link Some}
     * @param none the function to call if <code>this</code> is {@link None}
     */
    public abstract void match(
            @NonNull Consumer<T> some,
            @NonNull Runnable none
    );

    /**
     * Depending on whether <code>this</code> is {@link Some}
     * or {@link None}, this method calls 1 of 2 functions with the
     * stored value (if present).
     * @param some the function to call if <code>this</code> is {@link Some}
     * @param none the function to call if <code>this</code> is {@link None}
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public abstract <R> R resolve(
            @NonNull Function<T, R> some,
            @NonNull Supplier<R> none
    );

    @Override
    public abstract boolean equals(@Nullable Object o);

    @Override
    public abstract int hashCode();
}
