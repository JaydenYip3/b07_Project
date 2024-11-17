package com.b07.planetze.ecotracker;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * An <code>ActivityForm</code> input field.
 */
public sealed interface ActivityField {
    /**
     * A single selection from a list of choices.
     */
    final class Choice<T> implements ActivityField {
        private final List<T> choices;

        /**
         * Creates a choice field from a list of choices.
         * @param choices the list of choices
         */
        public Choice(@NonNull List<T> choices) {
            this.choices = choices;
        }
    }

    /**
     * A positive integer.
     */
    final class PositiveInt implements ActivityField {
        /**
         * Creates a positive int field.
         */
        public PositiveInt() {}
    }
}
