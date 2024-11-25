package com.b07.planetze.common;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Represents a User with an ID and a username.
 */
public class User {
    private final UserId id;
    private String username;

    private boolean compolete_survey;

    // Constructor
    public User(@NonNull UserId id, @NonNull String username) {
        this.id = id;
        this.username = username;
        this.compolete_survey = false;

    }
    // Getters
    public String getUserId() {
        return id.getUserId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }



}
