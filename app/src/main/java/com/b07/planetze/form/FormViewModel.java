package com.b07.planetze.form;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.util.option.Option;

import java.util.HashSet;
import java.util.Set;

public final class FormViewModel extends ViewModel {
    @NonNull private static final String TAG = "FormViewModel";
    @NonNull
    private final MutableLiveData<Option<FormOptions>> form;
    @NonNull
    private final MutableLiveData<Boolean> isCancelled;
    private final MutableLiveData<Boolean> isDeleted;
    @NonNull
    private final MutableLiveData<Option<FormSubmission>> submission;
    @NonNull
    private final MutableLiveData<Set<Integer>> missingFields;

    public FormViewModel() {
        this.form = new MutableLiveData<>(none());
        this.isCancelled = new MutableLiveData<>(false);
        this.isDeleted = new MutableLiveData<>(false);
        this.submission = new MutableLiveData<>(none());
        this.missingFields = new MutableLiveData<>(new HashSet<>(0));
    }

    public void setForm(@NonNull Option<FormOptions> form) {
        this.form.setValue(form);
    }

    public LiveData<Option<FormOptions>> getForm() {
        return form;
    }

    public LiveData<Boolean> getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean b) {
        isCancelled.setValue(b);
    }

    public LiveData<Boolean> getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean b) {
        isDeleted.setValue(b);
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

    public void reset() {
        setSubmission(none());
        setIsDeleted(false);
        setIsCancelled(false);
        setForm(none());
        setMissingFields(new HashSet<>(0));
    }

    public void removeObservers(LifecycleOwner owner) {
        isCancelled.removeObservers(owner);
        isDeleted.removeObservers(owner);
        submission.removeObservers(owner);
    }
}
