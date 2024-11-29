package com.b07.planetze.form;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.exception.FormException;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.None;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FormViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Option<Form>> form;
    @NonNull
    private final MutableLiveData<Option<FormSubmission>> submission;
    @NonNull
    private final MutableLiveData<Set<Integer>> missingFields;

    public FormViewModel() {
        this.form = new MutableLiveData<>(none());
        this.submission = new MutableLiveData<>(none());
        this.missingFields = new MutableLiveData<>(new HashSet<>(0));
    }

    public void setForm(@NonNull Option<Form> form) {
        this.form.setValue(form);
    }

    public LiveData<Option<Form>> getForm() {
        return form;
    }

    public void setSubmission(@NonNull Option<FormSubmission> submission) {
        this.submission.setValue(submission);
    }

    @NonNull
    public LiveData<Option<FormSubmission>> getSubmission() {
        return submission;
    }

    public void setMissingFields(@NonNull Set<Integer> missingFields) {
        this.missingFields.setValue(missingFields);
    }

    @NonNull
    public LiveData<Set<Integer>> getMissingFields() {
        return missingFields;
    }
}
