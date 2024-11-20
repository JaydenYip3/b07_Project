package com.b07.planetze.util.option;

import androidx.annotation.NonNull;

import com.b07.planetze.util.immutability.MutableWithCopy;
import com.b07.planetze.util.result.Ok;
import com.b07.planetze.util.result.Result;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents the presence of an optional value.
 * @param <T> the type of the value
 */
public final class Some<T> extends Option<T> {
    @NonNull private final T value;

    /**
     * Creates an {@link Option} with a present value.
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

    @Override
    public Some<T> apply(@NonNull Consumer<T> f) {
        f.accept(value);
        return this;
    }

    @Override
    public Some<T> applyNone(@NonNull Runnable f) {
        return this;
    }

    @Override
    @NonNull
    public <U> Some<U> map(@NonNull Function<T, U> f) {
        return new Some<>(f.apply(value));
    }

    @NonNull
    @Override
    public Some<T> or(@NonNull Option<T> other) {
        return this;
    }

    @NonNull
    @Override
    public Some<T> or(@NonNull Supplier<Option<T>> supplier) {
        return this;
    }

    @NonNull
    @Override
    public T getOr(@NonNull T defaultValue) {
        return value;
    }

    @NonNull
    @Override
    public T getOr(@NonNull Supplier<T> supplier) {
        return value;
    }

    @NonNull
    @Override
    public <E> Ok<T, E> okOr(@NonNull E error) {
        return new Ok<>(value);
    }

    @NonNull
    @Override
    public <E> Ok<T, E> okOr(@NonNull Supplier<E> supplier) {
        return new Ok<>(value);
    }

    @Override
    public boolean isSomeAnd(@NonNull Predicate<T> predicate) {
        return predicate.test(value);
    }

    @Override
    public void match(@NonNull Consumer<T> some, @NonNull Runnable none) {
        some.accept(value);
    }

    @Override
    public <R> R match(
            @NonNull Function<T, R> some,
            @NonNull Supplier<R> none
    ) {
        return some.apply(value);
    }

    @NonNull
    @Override
    public Some<T> copy() {
        if (value instanceof MutableWithCopy<?> v) {
            @SuppressWarnings("unchecked")
            T copied = (T) v.copy();

            return new Some<>(copied);
        }
        return new Some<>(value);
    }
}
