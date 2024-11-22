package com.b07.planetze.util.option;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.util.result.Error;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents the absence of an optional value.
 * @param <T> the type of the value
 */
public final class None<T> extends Option<T> {
    /**
     * Creates an {@link Option} with an absent value.
     */
    public None() {}

    @Override
    public None<T> apply(@NonNull Consumer<T> f) {
        return this;
    }

    @Override
    public None<T> applyNone(@NonNull Runnable f) {
        f.run();
        return this;
    }

    @NonNull
    @Override
    public <U> None<U> map(@NonNull Function<T, U> f) {
        return new None<>();
    }

    @NonNull
    @Override
    public Option<T> or(@NonNull Option<T> other) {
        return other;
    }

    @NonNull
    @Override
    public Option<T> orElse(@NonNull Supplier<Option<T>> supplier) {
        return supplier.get();
    }

    @NonNull
    @Override
    public T getOr(@NonNull T defaultValue) {
        return defaultValue;
    }

    @NonNull
    @Override
    public T getOrElse(@NonNull Supplier<T> supplier) {
        return supplier.get();
    }

    @NonNull
    @Override
    public T getOrThrow(@NonNull RuntimeException exception) {
        throw exception;
    }

    @NonNull
    @Override
    public T getOrThrow(@NonNull Supplier<RuntimeException> supplier) {
        throw supplier.get();
    }

    @NonNull
    @Override
    public <E> Error<T, E> okOr(@NonNull E error) {
        return new Error<>(error);
    }

    @NonNull
    @Override
    public <E> Error<T, E> okOr(@NonNull Supplier<E> supplier) {
        return new Error<>(supplier.get());
    }

    @Override
    public boolean isSomeAnd(@NonNull Predicate<T> predicate) {
        return false;
    }

    @Override
    public void match(@NonNull Consumer<T> some, @NonNull Runnable none) {
        none.run();
    }

    @Override
    public <R> R resolve(
            @NonNull Function<T, R> some,
            @NonNull Supplier<R> none
    ) {
        return none.get();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return o instanceof None;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @NonNull
    @Override
    public None<T> copy() {
        return new None<>();
    }

    @NonNull
    @Override
    public None<T> self() {
        return this;
    }
}
