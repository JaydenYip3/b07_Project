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
import com.b07.planetze.common.measurement.Distance;
import com.b07.planetze.common.measurement.Duration;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.form.Form;
import com.b07.planetze.form.FormFragment;
import com.b07.planetze.form.FormSubmission;
import com.b07.planetze.form.FormViewModel;
import com.b07.planetze.form.definition.FieldId;
import com.b07.planetze.form.definition.FormBuilder;
import com.b07.planetze.form.field.ChoiceField;
import com.b07.planetze.form.field.DistanceField;
import com.b07.planetze.form.field.DurationField;
import com.b07.planetze.form.field.IntField;
import com.b07.planetze.form.field.MassField;
import com.b07.planetze.util.immutability.ImmutableCopy;
import com.b07.planetze.util.option.Some;

public final class EcoTrackerActivity extends AppCompatActivity {
    @NonNull private static final String TAG = "EcoTrackerActivity";
    private FormViewModel formModel;
    private EcoTrackerViewModel ecoModel;

    public static void start(Context context) {
        Intent intent = new Intent(context, EcoTrackerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ecotracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        formModel = new ViewModelProvider(this).get(FormViewModel.class);
        ecoModel = new ViewModelProvider(this).get(EcoTrackerViewModel.class);

        FormBuilder fb = new FormBuilder();
        FieldId<Integer> f1 = fb.add("f1", ChoiceField
                .withChoices("c1", "c2"));

        FieldId<ImmutableCopy<Duration>> f2 = fb.add("f2",
                DurationField.create());

        FieldId<Integer> f3 = fb.add("f3", ChoiceField
                .withChoices("c3", "c4", "c5")
                .initially(2));

        FieldId<Integer> f4 = fb.add("f4", IntField.POSITIVE
                .initially(2));

        Form form = fb.build().createForm();

        formModel.setForm(form);
        formModel.getSubmission().observe(this, submission -> {
            if (!(submission instanceof Some<FormSubmission> some)) {
                return;
            }
            FormSubmission sub = some.get();

            Log.d(TAG, sub.get(f2).copy().toString());
        });

        loadFragment(FormFragment.newInstance());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ecotracker_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}