package com.b07.planetze.onboarding;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.immutability.ImmutableCopy;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Ok;

import java.util.function.Consumer;

public final class OnboardingViewModel extends ViewModel {
    @NonNull private final String TAG = "OnboardingViewModel";

    @NonNull private final MutableLiveData<Option<OnboardingScreen>> screen;
    @NonNull private final Emissions emissions;
    @NonNull private final Database db;

    public OnboardingViewModel() {
        screen = new MutableLiveData<>(none());
        emissions = Emissions.zero();
        db = new FirebaseDb();
    }

    public void setScreen(@NonNull OnboardingScreen screen) {
        this.screen.setValue(some(screen));
    }

    @NonNull
    public LiveData<Option<OnboardingScreen>> getScreen() {
        return screen;
    }

    @NonNull
    public Emissions emissions() {
        return emissions;
    }

    public void updateUserCountry(@NonNull String country) {
        db.fetchUser(r -> {
            if (!(r instanceof Ok<Option<User>, ?> ok)) {
                return;
            }
            ok.get().apply(user -> {
                var newUser = new User(user.name(), user.email(), country);

                db.postUser(newUser, r2 -> r2.match(
                        u -> Log.d(TAG, "user country set"),
                        e -> Log.d(TAG, e.message())
                ));
            });
        });
    }

    public void postOnboardingEmissions(@NonNull ImmutableCopy<Emissions> emissions) {
        Emissions copy = emissions.copy();
        db.postOnboardingEmissions(copy, r -> {
            r.match(u -> {
                Log.d(TAG, "posted onboarding emissions: " + copy);
            }, e -> {
                Log.d(TAG, "failed to post onboarding emissions: " + e.message());
            });
        });
    }

    public void fetchUser(@NonNull Consumer<User> callback) {
        db.fetchUser(r -> {
            r.match(maybeUser -> {
                maybeUser.apply(callback);
            }, e -> {
                Log.d(TAG, "failed to fetch user: " + e.message());
            });
        });
    }
}
