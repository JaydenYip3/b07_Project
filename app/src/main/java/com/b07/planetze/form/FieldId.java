package com.b07.planetze.form;

public class FieldId<V extends FieldValue> {
    int id;
    public FieldId(int id) {
        this.id = id;
    }
    public int get() {
        return id;
    }
}
