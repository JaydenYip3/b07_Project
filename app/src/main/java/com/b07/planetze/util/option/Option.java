package com.b07.planetze.util.option;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.result.Err;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Holds an optional value—useful for code explicitness and
 * for avoiding <code>null</code>. <br>
 * Structurally, this is a sum type of variants {@link Some} and {@link None},
 * representing the presence and absence of the value, respectively.
 * @param <T> the type of the value
 */
public sealed abstract class Option<T>
        implements MutableWithCopy<Option<T>>
        permits Some, None {
    /**
     * Creates an {@link Option} from a nullable value.
     * @param value the value
     * @return {@link Some} if the value is non-null; {@link None} otherwise.
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
     * Applies a function to the held value iff it is present.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Option<T> apply(@NonNull Consumer<T> f);

    /**
     * Calls a function if <code>this</code> is {@link None}.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Option<T> applyNone(@NonNull Runnable f);

    /**
     * Creates a new {@link Some} by applying a function to the held value
     * if it is present—otherwise creates a new {@link None}.
     *
     * @param f the function
     * @return a new {@link Option}
     * @param <U> the return type of the function
     */
    @NonNull
    public abstract <U> Option<U> map(@NonNull Function<T, U> f);

    /**
     * Returns <code>this</code> if <code>this</code> is {@link Some};
     * otherwise, returns another {@link Option}.
     * @param other the other {@link Option}
     * @return <code>this</code> or <code>other</code>
     */
    @NonNull
    public abstract Option<T> or(@NonNull Option<T> other);

    /**
     * Returns <code>this</code> if <code>this</code> is {@link Some};
     * otherwise, returns the output of a function.
     * @param supplier the function
     * @return <code>this</code> or the output of <code>supplier</code>
     */
    @NonNull
    public abstract Option<T> or(@NonNull Supplier<Option<T>> supplier);

    /**
     * Returns the held value if it is present; otherwise, returns a
     * default value.
     * @param defaultValue the default value
     * @return the held value or the default value
     */
    @NonNull
    public abstract T getOr(@NonNull T defaultValue);

    /**
     * Returns the held value if it is present; otherwise, returns the
     * output of a function
     * @param supplier the function
     * @return the held value or the output of <code>supplier</code>
     */
    @NonNull
    public abstract T getOr(@NonNull Supplier<T> supplier);

    /**
     * Creates an {@link Ok} with the held value if it is present; otherwise,
     * creates a {@link Err} with a given error.
     * @param error the error
     * @return a new {@link Result}
     * @param <E> the type of the error
     */
    @NonNull
    public abstract <E> Result<T, E> okOr(@NonNull E error);

    /**
     * Creates an {@link Ok} with the held value if it is present; otherwise,
     * creates a {@link Err} with an error given by the output of a function.
     * @param supplier the function
     * @return a new {@link Result}
     * @param <E> the type of the error
     */
    @NonNull
    public abstract <E> Result<T, E> okOr(@NonNull Supplier<E> supplier);

    /**
     * Returns <code>true</code> iff <code>this</code> is {@link Some}. <br>
     * If you require the held value, use <code>if (x instanceof Some&lt;T&gt; some)</code>
     * or {@link Option#match}.
     * @return <code>true</code> iff <code>this</code> is {@link Some}.
     */
    public boolean isSome() {
        return this instanceof Some<T>;
    }

    /**
     * Calls a function with the held value if <code>this</code> is
     * {@link Some}.
     * Returns <code>true</code> iff the function is called and outputs <code>true</code>.
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
    public abstract <R> R match(
            @NonNull Function<T, R> some,
            @NonNull Supplier<R> none
    );
}
