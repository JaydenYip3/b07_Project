package com.b07.planetze.ecogauge;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public final class EcoGaugeViewModel extends ViewModel {
    @NonNull
    private final String TAG = "EcoGaugeViewModel";

    @NonNull private final MutableLiveData<String> interval;

    public EcoGaugeViewModel() {
        this.interval = new MutableLiveData<>("Week");
    }

    public void setInterval(@NonNull String itv) {
        this.interval.setValue(itv);
    }

    public LiveData<String> getInterval() {
        return interval;
    }
}
