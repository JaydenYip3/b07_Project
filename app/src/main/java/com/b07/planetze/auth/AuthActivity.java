package com.b07.planetze.auth;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.common.UserId;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.ecotracker.habits.HabitSuggestionsFragment;
import com.b07.planetze.onboarding.QuestionsTransportationFragment;
import com.b07.planetze.util.option.Option;
import com.b07.planetze.util.option.Some;
import com.b07.planetze.util.result.Ok;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An activity that deals with user authentication.
 */
public class AuthActivity extends AppCompatActivity implements LoginCallback, RegisterCallback, ResetPasswordCallback, SendResetCallback, AuthScreenSwitch, EmailConfirmationCallback {
    private static final String TAG = "AuthActivity";
    private static final String EXTRA_INITIAL_SCREEN = "com.b07.planetze.AUTH_INITIAL_SCREEN";

    private FirebaseAuth auth;

    final Handler handler = new Handler();


    /**
     * Starts an AuthActivity.
     * @param context the context from which to start the activity
     * @param initialScreen the screen to display upon starting
     */
    public static void start(Context context, AuthScreen initialScreen) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(EXTRA_INITIAL_SCREEN, initialScreen);
        context.startActivity(intent);
    }

    @Override
    public void login(@NonNull String email, @NonNull String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email and password fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email address is malformed", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null && !user.isEmailVerified()){
                            switchScreens(AuthScreen.EMAIL_CONFIRMATION);
                            confirmEmail();
                        }
                        else{
                            Toast.makeText(this, "Logged in as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            FirebaseDb fdb = new FirebaseDb();
                            fdb.fetchOnboardingEmissions(result -> {
                                result.match(userOption -> { // if this operation was successful:
                                    userOption.match(// if the user has user info set:
                                            emissions -> {
                                                Log.d(TAG, "emissions found");
                                                loadFragment(new HabitSuggestionsFragment());
                                                //load ecotracker ---------------------------------------
                                            },
                                            () -> {
                                                Log.d(TAG, "emissions not found");
                                                loadFragment(new QuestionsTransportationFragment());
                                            });
                                }, dbError -> { // if this operation failed:
                                    Log.d(TAG, "error: " + dbError);
                                });
                            });
                        }

                    } else {
                        Exception e = task.getException();

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "login error: ", e);
                        }
                    }
                });
    }

    @Override
    public void register(@NonNull String email, @NonNull String password, @NonNull String confirmPassword, @NonNull String username) {
        if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this, verificationTask -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "User registered successfully; Please verify your email address", Toast.LENGTH_LONG).show();
                        FirebaseUser user = auth.getCurrentUser();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
//                        User a = new User(new UserId(user.getUid()), username);

//                        db.getReference("Users").child(a.getUserId()).setValue(a);
//                        Map<String, Object> emptyActivities = new TreeMap<>();
//                        emptyActivities.put("00-00-0000", "Activity Object");


//                        db.getReference("Activities").child(a.getUserId()).setValue(pLikeduserId)
//                                .addOnCompleteListener(task2 -> {
//                                    if (task2.isSuccessful()) {
//                                        Toast.makeText(this, "Successfully wrote Activity object to Activities.",Toast.LENGTH_LONG).show();
//                                    } else {
//                                        Toast.makeText(this, "Failed to write Activity object: " + task.getException(), Toast.LENGTH_LONG).show();
//                                    }
//                                });

                        FirebaseDb fdb = new FirebaseDb();
                        Map<String, String> map = new HashMap<>();
                        map.put("name", username);
                        map.put("email", email);
                        map.put("country", "");
                        User currentUser = User.fromJson(map);
                        fdb.postUser(currentUser, result -> {
                            result.match(fbuser -> Log.d(TAG, "user made")
                                    , dbError -> { // if this operation failed:
                                        Log.d(TAG, "error: " + dbError);
                                    });
                        });

                        switchScreens(AuthScreen.EMAIL_CONFIRMATION);
                        confirmEmail();

                    } else {
                        Toast.makeText(this, verificationTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                // Handle errors
                try {
                    throw task.getException();
                } catch (FirebaseAuthUserCollisionException e) {
                    Toast.makeText(this, "This email is already in use by another account.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Sign-up failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public void confirmEmail(){
        Handler handler = new Handler();
        Runnable checkEmailVerification = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = auth.getCurrentUser();
                user.reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isVerified = user.isEmailVerified();
                        if (isVerified) {
                            Toast.makeText(getApplicationContext(), "Email verified! Access granted.", Toast.LENGTH_SHORT).show();
                            loadFragment(new QuestionsTransportationFragment());
                        } else {
                            handler.postDelayed(this, 5000);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error checking email verification status.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        handler.post(checkEmailVerification);
    }

    public void resendConfirmationEmail(){
        //TODO check for valid user in system
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        currentUser.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Email has been sent.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Failed to send email. Try again later.", Toast.LENGTH_LONG).show();
                    }
                });

    }


    @Override
    public void sendPasswordResetEmail(@NonNull String email) {
        Log.d(TAG, "sending password reset email to " + email);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "failed to send reset email: ", task.getException());
                        Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void resetPassword(@NonNull String code, @NonNull String newPassword, @NonNull String confirmPassword) {
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.verifyPasswordResetCode(code)
                .addOnCompleteListener(verifyTask -> {
                    if (verifyTask.isSuccessful()) {
                        auth.confirmPasswordReset(code, newPassword)
                                .addOnCompleteListener(resetTask -> {
                                    if (resetTask.isSuccessful()) {
                                        Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                                        switchScreens(AuthScreen.LOGIN);
                                    } else {
                                        Log.e(TAG, "Password reset failed", resetTask.getException());
                                        Toast.makeText(this, "Password reset failed: " + resetTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Log.e(TAG, "Invalid reset code", verifyTask.getException());
                        Toast.makeText(this, "Invalid code", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void switchScreens(AuthScreen screen) {
        switch(screen) {
            case LOGIN -> loadFragment(new LoginFragment());
            case REGISTER -> loadFragment(new RegisterFragment());
            case RESET_PASSWORD -> loadFragment(new ResetPasswordFragment());
            case SEND_PASSWORD_RESET -> loadFragment(new SendResetFragment());
            case EMAIL_CONFIRMATION -> loadFragment(new EmailConfirmationFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new AuthActivityInitException("EXTRA_INITIAL_SCREEN not specified in intent extras");
        }

        AuthScreen initialScreen = (AuthScreen) extras.getSerializable(EXTRA_INITIAL_SCREEN);
        if (initialScreen == null) {
            throw new AuthActivityInitException("invalid EXTRA_INITIAL_SCREEN specified in intent extras");
        }

        switchScreens(initialScreen);

        auth = FirebaseAuth.getInstance();
    }
}