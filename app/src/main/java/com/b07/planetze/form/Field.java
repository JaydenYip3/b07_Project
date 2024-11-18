package com.b07.planetze.form;

import androidx.annotation.NonNull;

import com.b07.planetze.util.None;
import com.b07.planetze.util.Option;

import java.util.List;

/**
 * An input field.
 */
public sealed interface Field {
    /**
     * A single selection from a list of choices.
     */
    final class Choice<T extends ChoiceType<T>> implements Field {
        private Option<Integer> selection;

        /**
         * Creates a choice field.
         */
        public Choice() {
            this.selection = new None<>();
        }

        public Option<T> value() {
            return
        }
    }

    /**
     * A positive integer.
     */
    final class PositiveInt implements Field {
        /**
         * Creates a positive int field.
         */
        public PositiveInt() {}
    }
}
