package com.b07.planetze.form;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.form.exception.FormException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;

import java.util.List;

public final class FormViewModel extends ViewModel {
    @NonNull private final MutableLiveData<Option<Form>> form;
    @NonNull private final MutableLiveData<Option<FormSubmission>> submission;

    public FormViewModel() {
        this.form = new MutableLiveData<>(new None<>());
        this.submission = new MutableLiveData<>(new None<>());
    }

    public void setForm(@NonNull Form form) {
        this.form.setValue(new Some<>(form));
    }

    public LiveData<Option<Form>> getForm() {
        return form;
    }

    public LiveData<Option<FormSubmission>> getSubmission() {
        return submission;
    }

    public Result<Unit, List<Integer>> submit() {
        Form f = Option
                .flattenNull(form.getValue())
                .getOrThrow(new FormException("submit called before setForm"));

        return f.submit()
                .map(Some::new)
                .apply(submission::setValue)
                .map(x -> Unit.UNIT);
    }
}
