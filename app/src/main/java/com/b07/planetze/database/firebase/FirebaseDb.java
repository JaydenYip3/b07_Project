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
import com.b07.planetze.database.data.DailyData;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

        db.child("dailies").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DailyId id = new DailyId(Objects.requireNonNull(snapshot.getKey()));
                DailyData d = DailyData.fromJson(snapshot.getValue());
                dailies.put(id, d.date(), d.daily());
                Log.d(TAG, "onChildAdded: " + id.get());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DailyId id = new DailyId(Objects.requireNonNull(snapshot.getKey()));
                DailyData d = DailyData.fromJson(snapshot.getValue());
                dailies.put(id, d.date(), d.daily());

                Log.d(TAG, "onChildChanged: " + id.get());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                DailyId id = new DailyId(Objects.requireNonNull(snapshot.getKey()));
                dailies.remove(id);

                Log.d(TAG, "onChildRemoved: " + id.get());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Log.w(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    @SuppressWarnings({"ConstantConditions"})
    @NonNull
    private static <T> Result<Option<T>, DatabaseError> mapTask(
            @NonNull Task<DataSnapshot> task,
            @NonNull Function<Object, T> deserializer
    ) {
        return task.isSuccessful()
                ? ok(Option.mapNull(task.getResult().getValue()).map(deserializer))
                : error(new FirebaseError(task.getException()));
    }

    @NonNull
    private static Result<Unit, DatabaseError> mapFirebaseError(
            @Nullable com.google.firebase.database.DatabaseError err
    ) {
        return err == null ? ok() : error(new FirebaseError(err.getMessage()));
    }

    public void postUser(
            @NonNull User user,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        db.child("users").child(userId).setValue(user.toJson(),
                (err, ref) -> callback.accept(mapFirebaseError(err)));
    }

    public void fetchUser(@NonNull Consumer<Result<Option<User>, DatabaseError>> callback) {
        db.child("users").child(userId).get().addOnCompleteListener(
                task -> callback.accept(mapTask(task, User::fromJson)));
    }

    public void postOnboardingEmissions(
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        db.child("onboardingEmissions").child(userId).setValue(emissions.toJson(),
                (err, ref) -> callback.accept(mapFirebaseError(err)));
    }

    public void fetchOnboardingEmissions(@NonNull Consumer<Result<Option<Emissions>, DatabaseError>> callback) {
        db.child("onboardingEmissions").child(userId).get().addOnCompleteListener(
                task -> callback.accept(mapTask(task, Emissions::fromJson)));
    }

    public void postDaily(
            @NonNull LocalDate date,
            @NonNull Daily daily,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        DailyData d = new DailyData(date, daily);
        db.child("dailies").child(userId).push().setValue(d.toJson(),
                (err, ref) -> callback.accept(mapFirebaseError(err)));
    }

    public void updateDaily(
            @NonNull DailyId id,
            @NonNull Daily update,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put(id.get(), update.toJson());

        db.child("dailies").child(userId).updateChildren(map,
                (err, ref) -> callback.accept(mapFirebaseError(err)));
    }

    public void deleteDaily(
            @NonNull DailyId id,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put(id.get(), null);

        db.child("dailies").child(userId).updateChildren(map,
                (err, ref) -> callback.accept(mapFirebaseError(err)));
    }

    public void fetchDailies(
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<DailyFetchList, DatabaseError>> callback
    ) {
        callback.accept(ok(dailies.getAllIn(interval)));
    }
}
