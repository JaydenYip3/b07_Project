package com.b07.planetze.database.firebase;

import static com.b07.planetze.util.result.Result.ok;
import static com.b07.planetze.util.result.Result.error;
import static com.b07.planetze.util.option.Option.some;
import static com.b07.planetze.util.option.Option.none;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.common.UserId;
import com.b07.planetze.common.daily.Daily;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.DatabaseException;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.data.DailyId;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.Unit;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.result.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;

public final class FirebaseDb implements Database {
    @NonNull private final DatabaseReference db;
    @NonNull private final String userId;

    public FirebaseDb() {
        this.db = FirebaseDatabase
                .getInstance("https://planetze-3cc9d-default-rtdb.firebaseio.com")
                .getReference();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        this.userId = Option.mapNull(auth.getCurrentUser())
                .getOrThrow(new DatabaseException("User not logged in"))
                .getUid();
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

    public void postUser(
            @NonNull User user,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        db.child("users").child(userId).setValue(user.toJson());
    }

    public void fetchUser(
            @NonNull Consumer<Result<Option<User>, DatabaseError>> callback
    ) {
        db.child("users").child(userId).get()
                .addOnCompleteListener(task -> callback.accept(map(task, User::fromJson)));
    }

    public void postOnboardingEmissions(
            @NonNull Emissions emissions,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {
        db.child("onboarding").child(userId).get()
                .addOnCompleteListener(task -> callback.accept(map))
    }

    public void fetchOnboardingEmissions(
            @NonNull Consumer<Result<Emissions, DatabaseError>> callback
    ) {
        db.child
    }

    public void postDaily(
            @NonNull LocalDate date,
            @NonNull Daily daily,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {

    }

    public void updateDaily(
            @NonNull DailyId id,
            @NonNull Daily update,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {

    }

    public void deleteDaily(
            @NonNull DailyId id,
            @NonNull Consumer<Result<Unit, DatabaseError>> callback
    ) {

    }

    public void fetchDailies(
            @NonNull DateInterval interval,
            @NonNull Consumer<Result<DailyFetchList, DatabaseError>> callback
    ) {

    }
}
