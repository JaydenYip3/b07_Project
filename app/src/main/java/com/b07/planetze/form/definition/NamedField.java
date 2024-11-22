package com.b07.planetze.form.definition;

import androidx.annotation.NonNull;

import com.b07.planetze.form.field.Field;

public record NamedField<T>(String name, Field<T> get) {}
