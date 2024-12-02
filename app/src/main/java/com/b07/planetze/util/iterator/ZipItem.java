package com.b07.planetze.util.iterator;

import androidx.annotation.NonNull;

public record ZipItem<L, R>(@NonNull L left, @NonNull R right) {}
