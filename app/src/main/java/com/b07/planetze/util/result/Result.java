package com.b07.planetze.util.result;

import androidx.annotation.NonNull;

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
 * {@link Err} (failure), with each variant holding a value.
 * @param <T> the type of the value stored by the {@link Ok} variant
 * @param <E> the type of the error stored by the {@link Err} variant
 */
public sealed abstract class Result<T, E> permits Ok, Err {
    /**
     * Applies a function to the held value iff <code>this</code> is {@link Ok}.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Result<T, E> apply(@NonNull Consumer<T> f);

    /**
     * Applies a function to the held error value iff <code>this</code> is
     * {@link Err}.
     * @param f the function
     * @return <code>this</code>
     */
    public abstract Result<T, E> applyError(@NonNull Consumer<E> f);

    /**
     * If <code>this</code> is {@link Ok}, creates a new {@link Ok} by applying
     * a function to the held value; otherwise, creates a new {@link Err}
     * with the error value unchanged.
     * @param f the function
     * @return a new {@link Result}
     * @param <U> the return type of the function
     */
    @NonNull
    public abstract <U> Result<U, E> map(@NonNull Function<T, U> f);

    /**
     * If <code>this</code> is {@link Err}, creates a new {@link Err} by
     * applying a function to the held error value; otherwise, creates a new
     * {@link Ok} with the value unchanged.
     * @param f the function
     * @return a new {@link Result}
     * @param <F> the return type of the function
     */
    @NonNull
    public abstract <F> Result<T, F> mapErr(@NonNull Function<E, F> f);

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
     * Returns <code>true</code> iff <code>this</code> is {@link Ok}. <br>
     * If you require the held value, use <code>if (x instanceof Ok&lt;T, E&gt; ok)</code>
     * or {@link Result#match}.
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
     * Returns <code>true</code> iff <code>this</code> is {@link Err}. <br>
     * If you require the held value, use <code>if (x instanceof Err&lt;T, E&gt; ok)</code>
     * or {@link Result#match}.
     * @return <code>true</code> iff <code>this</code> is {@link Err}.
     */
    public boolean isErr() {
        return this instanceof Err<T, E>;
    }

    /**
     * Calls a function with the held value if <code>this</code> is {@link Err}.
     * Returns <code>true</code> iff the function is called and outputs <code>true</code>.
     * @param predicate the function
     * @return <code>true</code> iff <code>this</code> is {@link Err} and
     *         <code>predicate(value)</code> returns <code>true</code>.
     */
    public abstract boolean isErrAnd(@NonNull Predicate<E> predicate);

    /**
     * Depending on whether <code>this</code> is {@link Ok} or {@link Err},
     * this method calls 1 of 2 functions with the associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Ok}
     * @param error the function to call if <code>this</code> is {@link Err}
     */
    public abstract void match(@NonNull Consumer<T> ok, @NonNull Consumer<E> error);

    /**
     * Depending on whether <code>this</code> is {@link Ok} or {@link Err},
     * this method calls 1 of 2 functions with the associated stored value.
     * @param ok the function to call if <code>this</code> is {@link Ok}
     * @param error the function to call if <code>this</code> is {@link Err}
     * @param <R> the return type of both functions
     * @return the value returned by whichever function was called
     */
    public abstract <R> R match(@NonNull Function<T, R> ok, @NonNull Function<E, R> error);
}
