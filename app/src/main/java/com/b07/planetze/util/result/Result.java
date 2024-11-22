package com.b07.planetze.util.result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.option.Option;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a success xor a failure. <br>
 * This allows for error handling in asynchronous contexts. <br>
 * This also allows for a higher level of abstraction if used as an alternative
 * to checked exceptions. <br>
 * Structurally, this is a sum type of variants {@link Ok} (success) and
 * {@link Error} (failure), with each variant holding a value.
 * @param <T> the type of the value stored by the {@link Ok} variant
 * @param <E> the type of the error stored by the {@link Error} variant
 */
public sealed abstract class Result<T, E>
        implements MutableWithCopy<Result<T, E>>
        permits Ok, Error {
    /**
     * Applies a function to the held value iff <code>this</code> is {@link Ok}.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Result<T, E> apply(@NonNull Consumer<T> f);

    /**
     * Applies a function to the held error value iff <code>this</code> is
     * {@link Error}.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Result<T, E> applyError(@NonNull Consumer<E> f);

    /**
     * If <code>this</code> is {@link Ok}, creates a new {@link Ok} by applying
     * a function to the held value; otherwise, creates a new {@link Error}
     * with the error value unchanged.
     * @param f the function
     * @return a new {@link Result}
     * @param <U> the return type of the function
     */
    @NonNull
    public abstract <U> Result<U, E> map(@NonNull Function<T, U> f);

    /**
     * If <code>this</code> is {@link Error}, creates a new {@link Error} by
     * applying a function to the held error value; otherwise, creates a new
     * {@link Ok} with the value unchanged.
     * @param f the function
     * @return a new {@link Result}
     * @param <F> the return type of the function
     */
    @NonNull
    public abstract <F> Result<T, F> mapError(@NonNull Function<E, F> f);

    /**
     * Returns the held value if <code>this</code> is {@link Ok}; otherwise,
     * returns a default value.
     * @param defaultValue the default value
     * @return the held value or the default value
     */
    @NonNull
    public abstract T getOr(@NonNull T defaultValue);

    /**
     * Returns the held value if <code>this</code> is {@link Ok}; otherwise,
     * returns the output of a function
     * @param supplier the function
     * @return the held value or the output of <code>supplier</code>
     */
    @NonNull
    public abstract T getOr(@NonNull Supplier<T> supplier);

    /**
     * Returns the held value if <code>this</code> is {@link Ok}. <br>
     * If <code>this</code> is {@link Error} and the held error is a
     * {@link RuntimeException}, this method throws the held error. Otherwise,
     * this method throws a {@link ResultException}.
     * @return the held value
     */
    @NonNull
    public abstract T expect();

    /**
     * {@return the held success value if it is present}
     */
    @NonNull
    public abstract Option<T> ok();

    /**
     * {@return the held error value if it is present}
     */
    @NonNull
    public abstract Option<E> error();

    /**
     * Returns <code>true</code> iff <code>this</code> is {@link Ok}. <br>
     * If you require the held value, use <code>if (x instanceof Ok&lt;T, E&gt; ok)</code>
     * or {@link Result#resolve}.
     * @return <code>true</code> iff <code>this</code> is {@link Ok}.
     */
    public boolean isOk() {
        return this instanceof Ok<T, E>;
    }

    /**
     * Calls a function with the held value if <code>this</code> is {@link Ok}.
     * Returns <code>true</code> iff the function is called and outputs <code>true</code>.
     * @param predicate the function
     * @return <code>true</code> iff <code>this</code> is {@link Ok} and
     *         <code>predicate(value)</code> returns <code>true</code>.
     */
    public abstract boolean isOkAnd(@NonNull Predicate<T> predicate);

    /**
     * Returns <code>true</code> iff <code>this</code> is {@link Error}. <br>
     * If you require the held value, use <code>if (x instanceof Error&lt;T, E&gt; ok)</code>
     * or {@link Result#resolve}.
     * @return <code>true</code> iff <code>this</code> is {@link Error}.
     */
    public boolean isError() {
        return this instanceof Error<T, E>;
    }

    /**
     * Calls a function with the held value if <code>this</code> is {@link Error}.
     * Returns <code>true</code> iff the function is called and outputs <code>true</code>.
     * @param predicate the function
     * @return <code>true</code> iff <code>this</code> is {@link Error} and
     *         <code>predicate(value)</code> returns <code>true</code>.
     */
    public abstract boolean isErrorAnd(@NonNull Predicate<E> predicate);

    /**
     * Depending on whether <code>this</code> is {@link Ok} or {@link Error},
     * this method calls 1 of 2 functions with the associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Ok}
     * @param error the function to call if <code>this</code> is {@link Error}
     */
    public abstract void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error);

    /**
     * Depending on whether <code>this</code> is {@link Ok} or {@link Error},
     * this method calls 1 of 2 functions with the associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Ok}
     * @param error the function to call if <code>this</code> is {@link Error}
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public abstract <R> R resolve(@NonNull Function<T, R> ok, @NonNull Function<E, R> error);

    @Override
    public abstract boolean equals(@Nullable Object o);
}
