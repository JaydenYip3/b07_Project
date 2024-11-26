package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.some;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.firebase.FirebaseDb;

import java.time.LocalDate;

public final class EcoTrackerViewModel extends ViewModel {
    @NonNull private final String TAG = "EcoTrackerViewModel";

    @NonNull private final MutableLiveData<EcoTrackerState> state;
    @NonNull private final Database db;

    public EcoTrackerViewModel() {
        // TODO: set actual default state
        this.state = new MutableLiveData<>(new EcoTrackerState.Form(DailyType.DRIVING));
        this.db = new FirebaseDb();
    }

    @NonNull
    public LiveData<EcoTrackerState> getState() {
        return state;
    }

    public void selectForm(@NonNull DailyType type) {
        state.setValue(new EcoTrackerState.Form(type));
    }

    public void submitDaily(@NonNull Daily daily) {
        db.postDaily(LocalDate.now(), daily, r -> {
            Log.d(TAG, "postDaily callback: " + r);
        });

        // TODO: transition to EcoTrackerState.ViewLogs
        state.setValue(new EcoTrackerState.SelectForm());
    }
}
