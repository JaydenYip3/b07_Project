package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.some;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.common.daily.DailyType;

public final class EcoTrackerViewModel extends ViewModel {
    @NonNull private final MutableLiveData<EcoTrackerState> state;

    public EcoTrackerViewModel() {
        // TODO: set actual default state
        this.state = new MutableLiveData<>(new EcoTrackerState.Form(DailyType.DRIVING));
    }

    @NonNull
    public LiveData<EcoTrackerState> getState() {
        return state;
    }

    public void selectForm(@NonNull DailyType type) {
        state.setValue(new EcoTrackerState.Form(type));
    }

    public void submitDaily(@NonNull Daily daily) {
        // TODO: call database

        // TODO: transition to EcoTrackerState.ViewLogs
        state.setValue(new EcoTrackerState.SelectForm());
    }
}
