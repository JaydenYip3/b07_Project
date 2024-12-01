package com.b07.planetze.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.b07.planetze.auth.backend.AuthBackend;
import com.b07.planetze.auth.backend.FirebaseAuthBackend;
import com.b07.planetze.auth.backend.error.OtherAuthError;
import com.b07.planetze.auth.backend.error.RegisterError;
import com.b07.planetze.common.User;
import com.b07.planetze.database.Database;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.home.HomeActivity;
import com.b07.planetze.onboarding.QuestionsTransportationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity that deals with user authentication.
 */
public class AuthActivity extends AppCompatActivity implements LoginCallback, RegisterCallback, SendResetCallback, AuthScreenSwitch, EmailConfirmationCallback {
    private static final String TAG = "AuthActivity";
    private static final String EXTRA_INITIAL_SCREEN = "com.b07.planetze.AUTH_INITIAL_SCREEN";

    private FirebaseAuth fbAuth;
    private AuthBackend auth;

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

    private void showToast(@NonNull String text) {
        int length = text.length() < 32
                ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
        Toast.makeText(this, text, length).show();
    }

    @Override
    public void login(@NonNull String email, @NonNull String password) {
        auth.login(email, password, r -> {
            r.match(user -> {
                if (!user.isEmailVerified()) {
                    switchScreens(AuthScreen.EMAIL_CONFIRMATION);
                    confirmEmail();
                    return;
                }

                showToast("Logged in as " + user.email());

                Database db = new FirebaseDb();
                db.fetchOnboardingEmissions(r2 -> {
                    r2.match(maybeEmissions -> {
                        maybeEmissions.match(emissions -> {
                            HomeActivity.start(this);
                        }, () -> {
                            loadFragment(new QuestionsTransportationFragment());
                        });
                    }, dbError -> {
                        showToast(dbError.message());
                    });
                });
            }, e -> {
                showToast(e.message());
            });
        });
    }

    @Override
    public void register(
            @NonNull String email,
            @NonNull String password,
            @NonNull String confirmPassword,
            @NonNull String username
    ) {
        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return;
        }
        auth.register(email, password, username, r -> {
            r.match(authUser -> {
                auth.sendEmailVerification(r2 -> {
                    r2.match(u -> {
                        var db = new FirebaseDb();
                        var user = new User(
                                username,
                                authUser.email(),
                                "Canada"
                        );
                        db.postUser(user, r3 -> {
                            r3.match(u2 -> {
                                showToast("User registered successfully; "
                                        + "please verify your email address");
                                switchScreens(AuthScreen.EMAIL_CONFIRMATION);
                                confirmEmail();
                            }, e -> {
                                showToast(e.message());
                            });
                        });
                    }, e -> {
                        showToast(e.message());
                    });
                });
            }, e -> {
                if (e instanceof RegisterError.UserCollision) {
                    showToast("This email is already in use.");
                } else {
                    showToast("Sign-up failed; please try again.");
                }
            });
        });
    }

    @Override
    public void confirmEmail(){
        Handler handler = new Handler();
        Runnable checkEmailVerification = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = fbAuth.getCurrentUser();
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

        auth.sendPasswordResetEmail(email, r -> {
            r.match(ok -> {
                showToast("Password reset email sent");
            }, e -> {
                if (e instanceof OtherAuthError) {
                    showToast("Error: " + e.message());
                } else {
                    showToast(e.message());
                }
            });
        });
    }

    @Override
    public void switchScreens(AuthScreen screen) {
        switch(screen) {
            case LOGIN -> loadFragment(new LoginFragment());
            case REGISTER -> loadFragment(new RegisterFragment());
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

        fbAuth = FirebaseAuth.getInstance();
        auth = new FirebaseAuthBackend();
    }
}