package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.some;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Error;
import com.b07.planetze.util.result.Ok;

import java.time.LocalDate;

public final class EcoTrackerViewModel extends ViewModel {
    @NonNull private final String TAG = "EcoTrackerViewModel";

    @NonNull private final MutableLiveData<EcoTrackerState> state;
    @NonNull private final MutableLiveData<DailyFetchList> dailies;
    @NonNull private final Database db;

    public EcoTrackerViewModel() {
        // TODO: set actual default state
        this.state = new MutableLiveData<>(new EcoTrackerState.Form(DailyType.DRIVING));
        this.dailies = new MutableLiveData<>(DailyFetchList.empty());
        this.db = new FirebaseDb();

        db.fetchDailies(DateInterval.day(LocalDate.now()), fetch -> {
            fetch.apply(dailies::setValue);
        });
    }

    @NonNull
    public LiveData<EcoTrackerState> getState() {
        return state;
    }

    public void selectForm(@NonNull DailyType type) {
        state.setValue(new EcoTrackerState.Form(type));
    }

    public void submitDaily(@NonNull Daily daily) {
//        db.postDaily(LocalDate.now(), daily, post -> {
//            Log.d(TAG, "postDaily callback: " + post);
//
//            db.fetchDailies(DateInterval.day(LocalDate.now()), fetch -> {
//                fetch.apply(dailies::setValue);
//            });
//        });

        // TODO: transition to EcoTrackerState.ViewLogs
        state.setValue(new EcoTrackerState.SelectForm());
    }
}
