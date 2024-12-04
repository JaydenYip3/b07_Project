package com.b07.planetze.home;

import static com.b07.planetze.util.option.Option.none;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.common.User;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.ecotracker.EcoTrackerState;
import com.b07.planetze.ecotracker.FormAction;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.option.Option;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Consumer;

public final class HomeViewModel extends ViewModel {
    @NonNull private final String TAG = "HomeViewModel";

    @NonNull private final MutableLiveData<DailyFetchList> dailies;
    @NonNull private final MutableLiveData<Option<User>> user;
    @NonNull private final MutableLiveData<Boolean> hasCompletedOnboarding;
    @NonNull private final Database db;

    public HomeViewModel() {
        this.dailies = new MutableLiveData<>(DailyFetchList.empty());
        this.user = new MutableLiveData<>(none());
        this.hasCompletedOnboarding = new MutableLiveData<>(true);
        this.db = new FirebaseDb();
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

    @NonNull
    public LiveData<Option<User>> getUser() {
        return user;
    }

    public void fetchUser() {
        db.fetchUser(r -> {
            r.apply(user::setValue);
        });
    }

    @NonNull
    public LiveData<Boolean> getHasCompletedOnboarding() {
        return hasCompletedOnboarding;
    }

    public void fetchOnboardingStatus() {
        db.fetchOnboardingEmissions(r -> {
            r.apply(opt -> hasCompletedOnboarding.setValue(opt.isSome()));
        });
    }

    public void fetchAll() {
        fetchDailies();
        fetchUser();
        fetchOnboardingStatus();
    }
}
