package com.b07.planetze.ecotracker;

import static com.b07.planetze.util.option.Option.some;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import com.b07.planetze.WelcomeFragment;
import com.b07.planetze.ecotracker.daily.DailyForm;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormFragment;
import com.b07.planetze.form.FormViewModel;

public final class EcoTrackerActivity extends AppCompatActivity {
    @NonNull private static final String TAG = "EcoTrackerActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoTrackerActivity.class);
        context.startActivity(intent);
    }

    private record Model(@NonNull EcoTrackerViewModel ecoTracker,
                         @NonNull FormViewModel form) {}

    private void startSelectForm(@NonNull Model model,
                                 @NonNull EcoTrackerState.SelectForm state) {
        Log.d(TAG, "test");
    }
    private void startForm(@NonNull Model model,
                           @NonNull EcoTrackerState.Form state) {
        model.form.getSubmission().removeObservers(this);

        DailyForm<?> df = state.dailyType().form();
        Form f = df.definition().createForm();

        model.form.getSubmission().observe(this, maybeSub -> {
            maybeSub.apply(sub -> {
                model.ecoTracker.submitDaily(df.formToDaily(sub));
            });
        });

        loadFragment(FormFragment.newInstance());
        model.form.setForm(f);
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
        loadFragment(new EcoTrackerHomeFragment());
//        Model model = new Model(
//                new ViewModelProvider(this).get(EcoTrackerViewModel.class),
//                new ViewModelProvider(this).get(FormViewModel.class)
//        );
//
//        model.ecoTracker.getState().observe(this, state -> {
//            if (state instanceof EcoTrackerState.Form form) {
//                startForm(model, form);
//            } else if (state instanceof EcoTrackerState.SelectForm selectForm) {
//                startSelectForm(model, selectForm);
//            }
//        });
    }


    private void loadFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ecotracker_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
