package com.b07.planetze.form;

import com.b07.planetze.util.Error;
import com.b07.planetze.util.ImmutableArray;
import com.b07.planetze.util.None;
import com.b07.planetze.util.Ok;
import com.b07.planetze.util.Option;
import com.b07.planetze.util.Result;
import com.b07.planetze.util.Some;
import com.b07.planetze.util.Unit;

/**
 * An input field.
 */
public interface Field {
    void set(Object o);
}
