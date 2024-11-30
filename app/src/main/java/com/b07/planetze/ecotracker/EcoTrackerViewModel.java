package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.some;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.daily.Daily;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.DateInterval;

import java.time.LocalDate;
import java.util.function.Consumer;

public final class EcoTrackerViewModel extends ViewModel {
    @NonNull private final String TAG = "EcoTrackerViewModel";

    @NonNull private final MutableLiveData<EcoTrackerState> state;
    @NonNull private final MutableLiveData<DailyFetchList> dailies;
    @NonNull private final Database db;

    public EcoTrackerViewModel() {
        this.state = new MutableLiveData<>(new EcoTrackerState.Home());
        this.dailies = new MutableLiveData<>(DailyFetchList.empty());
        this.db = new FirebaseDb();
    }

    @NonNull
    public LiveData<EcoTrackerState> getState() {
        return state;
    }

    @NonNull
    public LiveData<DailyFetchList> getDailies() {
        return dailies;
    }

    public void fetchDailies() {
        db.fetchDailies(DateInterval.day(LocalDate.now()), fetch -> {
            fetch.apply(dailies::setValue);
        });
    }

    public void newForm(@NonNull FormAction action) {
        state.setValue(new EcoTrackerState.Form(action));
    }

    public void cancelForm() {
        state.setValue(new EcoTrackerState.ViewLogs());
    }

    public void deleteDaily(@NonNull DailyId id,
                            @NonNull Consumer<DatabaseError> onError) {
        db.deleteDaily(id, r -> {
            r.applyError(onError);
            fetchDailies();
        });

        state.setValue(new EcoTrackerState.ViewLogs());
    }

    public void submitEditDaily(@NonNull DailyFetch fetch,
                                @NonNull Consumer<DatabaseError> onError) {
        db.updateDaily(fetch, r -> {
            r.applyError(onError);
            fetchDailies();
        });

        state.setValue(new EcoTrackerState.ViewLogs());
    }

    public void submitNewDaily(@NonNull Daily daily,
                               @NonNull Consumer<DatabaseError> onError) {
        db.postDaily(LocalDate.now(), daily, post -> {
            post.applyError(onError);
            fetchDailies();
        });

        state.setValue(new EcoTrackerState.ViewLogs());
    }

    public void toActivityLog() {
        state.setValue(new EcoTrackerState.ViewLogs());
    }

    public void toHome() {
        state.setValue(new EcoTrackerState.Home());
    }
}
