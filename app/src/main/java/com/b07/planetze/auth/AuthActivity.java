package com.b07.planetze.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.b07.planetze.onboarding.OnboardingActivity;
import com.b07.planetze.onboarding.QuestionsTransportationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * An activity that deals with user authentication.
 */
public class AuthActivity extends AppCompatActivity
        implements LoginView, RegisterCallback, AuthScreenSwitch,
        EmailConfirmationCallback {
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
    public static void start(@NonNull Context context, @NonNull AuthScreen initialScreen) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(EXTRA_INITIAL_SCREEN, initialScreen);
        context.startActivity(intent);
    }

    @Override
    public void showToast(@NonNull String text) {
        int length = text.length() < 32
                ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
        Toast.makeText(this, text, length).show();
    }

    @Override
    public void switchScreens(@NonNull AuthScreen screen) {
        switch(screen) {
            case LOGIN -> loadFragment(new LoginFragment());
            case REGISTER -> loadFragment(new RegisterFragment());
            case SEND_PASSWORD_RESET -> loadFragment(new SendResetFragment());
            case EMAIL_CONFIRMATION -> loadFragment(new EmailConfirmationFragment());
        }
    }

    @Override
    public void toHome() {
        HomeActivity.start(this);
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
    public void confirmEmail() {
        Handler handler = new Handler();

        Runnable onboard = () -> {
            OnboardingActivity.start(this);
        };

        Runnable checkEmailVerification = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = fbAuth.getCurrentUser();
                user.reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isVerified = user.isEmailVerified();
                        if (isVerified) {
                            Toast.makeText(getApplicationContext(), "Email verified! Access granted.", Toast.LENGTH_SHORT).show();
                            onboard.run();
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

    public void resendConfirmationEmail() {
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AuthScreen initialScreen = getAuthScreen();

        switchScreens(initialScreen);

        fbAuth = FirebaseAuth.getInstance();
        auth = new FirebaseAuthBackend();
    }

    @NonNull
    private AuthScreen getAuthScreen() {
        AuthScreen initialScreen;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new AuthActivityInitException("EXTRA_INITIAL_SCREEN not specified in intent extras");
        } else {
            initialScreen = (AuthScreen) extras.getSerializable(EXTRA_INITIAL_SCREEN);
            if (initialScreen == null) {
                throw new AuthActivityInitException("invalid EXTRA_INITIAL_SCREEN specified in intent extras");
            }
        }
        return initialScreen;
    }
}