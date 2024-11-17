package com.b07.planetze.common;

import androidx.annotation.NonNull;

/**
 * A user id wrapper. <br>
 * This exists to ensure that non-user-id strings
 * aren't used where user ids are expected.
 */
public class UserId {
    private String id;

    private UserId() {}

    /**
     * Creates a <code>UserId</code> given a user id
     * @param id the user id
     */
    public UserId(@NonNull String id) {
        this.id = id;
    }

    /**
     * Creates a copy of this id.
     * @return a new <code>UserId</code> of the same value
     */
    @NonNull
    public UserId copy() {
        return new UserId(id);
    }

    /**
     * Gets the user id.
     * @return the user id
     */
    @NonNull
    public String get() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserId other) {
            return id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
