package com.b07.planetze.util.iterator;

import androidx.annotation.NonNull;

public record EnumeratedItem<T>(int index, @NonNull T value) {}