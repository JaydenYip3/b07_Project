package com.b07.planetze.common;

import androidx.annotation.NonNull;

/**
 * A user id wrapper. <br>
 * This exists to ensure that non-user-id strings
 * aren't used where user ids are expected.
 */
public final class UserId {
    @NonNull private final String id;

    /**
     * Creates a {@link UserId} given a user id
     * @param id the user id
     */
    public UserId(@NonNull String id) {
        this.id = id;
    }

    /**
     * Gets the user id.
     * @return the user id
     */
    @NonNull
    public String getUserId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof UserId other) && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
