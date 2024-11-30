package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.none;
import static com.b07.planetze.util.option.Option.some;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.daily.DailyForm;
import com.b07.planetze.database.DatabaseError;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormFragment;
import com.b07.planetze.form.FormViewModel;
import com.b07.planetze.form.FormOptions;

public final class EcoTrackerActivity extends AppCompatActivity {
    @NonNull private static final String TAG = "EcoTrackerActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoTrackerActivity.class);
        context.startActivity(intent);
    }

    private record Model(@NonNull EcoTrackerViewModel ecoTracker,
                         @NonNull FormViewModel form) {}

    private void onSubmitError(@NonNull DatabaseError e) {
        Toast.makeText(
                this,
                "Failed to update database: " + e.message(),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void startForm(@NonNull Model model,
                           @NonNull EcoTrackerState.Form state) {
        model.form.removeObservers(this);

        Form f;
        if (state.action() instanceof FormAction.New action) {
            DailyForm<?> df = action.type().form();
            f = df.definition().createForm();

            model.form.getSubmission().observe(this, maybeSub -> {
                maybeSub.apply(sub -> {
                    model.form.reset();
                    model.ecoTracker.submitNewDaily(
                            df.formToDaily(sub), this::onSubmitError);
                });
            });

        } else {
            var action = (FormAction.Edit) state.action();
            DailyFetch fetch = action.fetch();
            DailyForm<?> df = fetch.type().form();
            f = df.dailyToForm(fetch.daily());

            model.form.getSubmission().observe(this, maybeSub -> {
                maybeSub.apply(sub -> {
                    model.form.reset();
                    model.ecoTracker.submitEditDaily(
                            fetch.withReplacedDaily(df.formToDaily(sub)),
                            this::onSubmitError
                    );
                });
            });

            model.form.getIsDeleted().observe(this, isDeleted -> {
                if (isDeleted) {
                    model.form.reset();
                    model.ecoTracker.deleteDaily(
                            fetch.id(), this::onSubmitError);
                }
            });
        }

        model.form.getIsCancelled().observe(this, isCancelled -> {
            if (isCancelled) {
                model.form.reset();
                model.ecoTracker.cancelForm();
            }
        });

        loadFragment(FormFragment.newInstance());

        var form = new FormOptions(
                f,
                state.action().formTitle(),
                state.action() instanceof FormAction.New
                        ? none() : some("Delete activity")
        );
        model.form.setForm(some(form));
    }

    private void startViewLogs(@NonNull Model model,
                               @NonNull EcoTrackerState.ViewLogs state) {
        model.form.removeObservers(this);
        model.form.reset();
        loadFragment(DailyLogsFragment.newInstance());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ecotracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                    systemBars.bottom);
            return insets;
        });

        Model model = new Model(
                new ViewModelProvider(this).get(EcoTrackerViewModel.class),
                new ViewModelProvider(this).get(FormViewModel.class)
        );

        model.ecoTracker.getState().observe(this, state -> {
            if (state instanceof EcoTrackerState.Form form) {
                startForm(model, form);
            } else if (state instanceof EcoTrackerState.ViewLogs viewLogs) {
                startViewLogs(model, viewLogs);
            }
        });
    }

    private void loadFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.ecotracker_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
