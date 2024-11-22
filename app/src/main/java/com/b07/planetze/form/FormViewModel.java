package com.b07.planetze.form;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;

public class FormViewModel extends ViewModel {
    private MutableLiveData<Option<FormDefinition>> definition;
    private Form form;

    public void setDefinition(@NonNull FormDefinition definition) {
        form = definition.createForm();
        if (this.definition == null) {
            this.definition = new MutableLiveData<>(new Some<>(definition));
        } else {
            this.definition.setValue(new Some<>(definition));
        }
    }

    public LiveData<Option<FormDefinition>> getDefinition() {
        if (this.definition == null) {
            this.definition = new MutableLiveData<>(new None<>());
        }
        return this.definition;
    }

    public <T> Result<Unit, String> setValue(
            @NonNull FieldId<T> id,
            @NonNull T value
    ) {
        return form.set(id, value);
    }

    public <T> Option<T> getValue(@NonNull FieldId<T> id) {
        return form.get(id);
    }
}