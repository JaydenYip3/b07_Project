package com.b07.planetze.form;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormDefinition;
import com.b07.planetze.form.exception.FormFragmentException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;

public class FormViewModel extends ViewModel {
    @NonNull private final MutableLiveData<Option<Form>> form;

    public FormViewModel() {
        this.form = new MutableLiveData<>(new None<>());
    }

    public void setForm(@NonNull Form form) {
        this.form.setValue(new Some<>(form));
    }

    public LiveData<Option<Form>> getForm() {
        return form;
    }
}
