package com.b07.planetze.database;

public class DatabaseError {
    private String message;

    private DatabaseError() {}

    public DatabaseError(String message) {
        this.message = message;
    }

    public String message() {
        return this.message;
    }
}
