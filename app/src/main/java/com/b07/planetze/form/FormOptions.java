package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.option.Option;

public record FormOptions(
        @NonNull Form form,
        @NonNull String title,
        @NonNull Option<String> deleteText
) {}
