package com.b07.planetze.form;

/**
 * Identifies a field associated with a {@link Form}. <br>
 * Use {@link FormBuilder} instead of creating these manually.
 * @param formId the id of the associated {@link Form}
 * @param index the index of the field
 * @param <V> the type of the field's value
 */
public record FieldId<V extends FieldValue>(FormId formId, int index) {
}
