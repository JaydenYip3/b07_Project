package com.b07.planetze.database;

import androidx.annotation.NonNull;

import java.util.Map;

public interface ToJsonSerializable {
    /**
     * {@return a <code>String</code>, <code>Long</code>, <code>Double</code>,
     *          <code>Boolean</code>, <code>Map&lt;String, Object&gt;</code>,
     *          or <code>List&lt;Object&gt;</code>}
     */
    @NonNull
    Object toJson();
}
