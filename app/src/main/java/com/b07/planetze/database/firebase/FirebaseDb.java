package com.b07.planetze.database.firebase;

import static com.b07.planetze.util.result.Result.ok;
import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.option.Option.some;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.daily.Daily;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.DatabaseException;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.database.data.DailyMap;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;

public final class FirebaseDb implements Database {
    @NonNull private static final String TAG = "FirebaseDb";
    @NonNull private final DatabaseReference db;
    @NonNull private final String userId;
    @NonNull private final DailyMap dailies;

    public FirebaseDb() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        this.userId = Option.mapNull(auth.getCurrentUser())
                .getOrThrow(new DatabaseException("User not logged in"))
                .getUid();

        this.db = FirebaseDatabase
                .getInstance("https://planetze-3cc9d-default-rtdb.firebaseio.com")
                .getReference();

        dailies = new DailyMap();

        db.child("dailies").child("userId").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {

            }
        });
    }

    @SuppressWarnings({"ConstantConditions"})
    @NonNull
    private static <T> Result<Option<T>, DatabaseError> map(
            @NonNull Task<DataSnapshot> task,
            @NonNull Function<Object, T> deserializer
    ) {
        return task.isSuccessful()
                ? ok(Option.mapNull(task.getResult().getValue()).map(deserializer))
                : error(new FirebaseError(task.getException()));
    }

    public void postUser(@NonNull User user) {
        db.child("users").child(userId).setValue(user.toJson());
    }

    public void fetchUser(@NonNull Consumer<Result<Option<User>, DatabaseError>> callback) {
        db.child("users").child(userId).get()
                .addOnCompleteListener(task -> callback.accept(map(task, User::fromJson)));
    }

    public void postOnboardingEmissions(@NonNull Emissions emissions) {
        db.child("onboarding_emissions").child(userId).setValue(emissions.toJson());
    }

    public void fetchOnboardingEmissions(@NonNull Consumer<Result<Option<Emissions>, DatabaseError>> callback) {
        db.child("onboarding_emissions").child(userId).get()
                .addOnCompleteListener(task -> callback.accept(map(task, Emissions::fromJson)));
    }

    public void postDaily(@NonNull LocalDate date, @NonNull Daily daily) {
        db.child("dailies").child(userId).push()
    }

    public void updateDaily(@NonNull DailyId id, @NonNull Daily update) {

    }

    public void deleteDaily(@NonNull DailyId id) {

    }

    public void fetchDailies(
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<DailyFetchList, DatabaseError>> callback
    ) {

    }
}
