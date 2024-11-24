package com.b07.planetze.form;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

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

    private int fragmentTagCounter;
    private int previousTagCounter;

    public FormViewModel() {
        this.form = new MutableLiveData<>(none());
        this.submission = new MutableLiveData<>(none());
        this.missingFields = new MutableLiveData<>(new HashSet<>(0));

        fragmentTagCounter = 0;
        previousTagCounter = 0;
    }

    public void setForm(@NonNull Form form) {
        this.form.setValue(some(form));
    }

    public LiveData<Option<Form>> getForm() {
        return form;
    }

    public LiveData<Option<FormSubmission>> getSubmission() {
        return submission;
    }

    public LiveData<Set<Integer>> getMissingFields() {
        return missingFields;
    }

    public void setTagCounter(int count) {
        previousTagCounter = fragmentTagCounter;
        fragmentTagCounter = count;
    }

    public int getPreviousTagCounter() {
        return previousTagCounter;
    }

    public int getTagCounter() {
        return fragmentTagCounter;
    }

    public void submit() {
        Form f = Option
                .flattenNull(form.getValue())
                .getOrThrow(new FormException("submit called before setForm"));

        f.submit()
                .map(Option::some)
                .match(submission::setValue, missingFields::setValue);
    }
}
