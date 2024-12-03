package com.b07.planetze.onboarding;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.measurement.Mass;

import java.time.LocalDate;

public class QuestionsFoodFragment extends Fragment {
    @NonNull private static final String TAG = "QuestionsFoodFragment";

    public QuestionsFoodFragment() {}

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);

        Button buttonMeat = view.findViewById(R.id.radioButton84);
        buttonMeat.setOnClickListener(v -> eatMeat(view));

        Button buttonNoMeat1 = view.findViewById(R.id.radioButton81);
        Button buttonNoMeat2 = view.findViewById(R.id.radioButton82);
        Button buttonNoMeat3 = view.findViewById(R.id.radioButton83);

        buttonNoMeat1.setOnClickListener(v -> NoMeat(view));
        buttonNoMeat2.setOnClickListener(v -> NoMeat(view));
        buttonNoMeat3.setOnClickListener(v -> NoMeat(view));

        ImageButton buttonBack = view.findViewById(R.id.back);
        buttonBack.setOnClickListener(v -> model.setScreen(OnboardingScreen.TRANSPORTATION));

        Button buttonNext = view.findViewById(R.id.next);
        buttonNext.setOnClickListener(v -> onSubmit(view, model));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions_food, container, false);
    }

    public void eatMeat(View view) {
        // Make all "Meat" related views visible
        view.findViewById(R.id.textView9).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9beef).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9pork).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9chkn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9fs).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9beef).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9pork).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9chkn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9fs).setVisibility(View.VISIBLE);

        // Update the text for question 10
        TextView question10 = view.findViewById(R.id.textView10);
        question10.setText(R.string.survey_question10);

        // Ensure question 10 remains in the correct position
        question10.setVisibility(View.VISIBLE);
    }
    public void onSubmit(@NonNull View v, @NonNull OnboardingViewModel model) {
        double foodEmissionsKgs = 0;

        RadioButton b81 = v.findViewById(R.id.radioButton81);
        RadioButton b82 = v.findViewById(R.id.radioButton82);
        RadioButton b83 = v.findViewById(R.id.radioButton83);
        RadioButton b84 = v.findViewById(R.id.radioButton84);
        RadioButton b91b = v.findViewById(R.id.radioButton91beef);
        RadioButton b92b = v.findViewById(R.id.radioButton92beef);
        RadioButton b93b = v.findViewById(R.id.radioButton93beef);
        RadioButton b94b = v.findViewById(R.id.radioButton94beef);
        RadioButton b91p = v.findViewById(R.id.radioButton91pork);
        RadioButton b92p = v.findViewById(R.id.radioButton92pork);
        RadioButton b93p = v.findViewById(R.id.radioButton93pork);
        RadioButton b94p = v.findViewById(R.id.radioButton94pork);
        RadioButton b91c = v.findViewById(R.id.radioButton91chkn);
        RadioButton b92c = v.findViewById(R.id.radioButton92chkn);
        RadioButton b93c = v.findViewById(R.id.radioButton93chkn);
        RadioButton b94c = v.findViewById(R.id.radioButton94chkn);
        RadioButton b91fs = v.findViewById(R.id.radioButton91fs);
        RadioButton b92fs = v.findViewById(R.id.radioButton92fs);
        RadioButton b93fs = v.findViewById(R.id.radioButton93fs);
        RadioButton b94fs = v.findViewById(R.id.radioButton94fs);
        RadioButton b101 = v.findViewById(R.id.radioButton101);
        RadioButton b102 = v.findViewById(R.id.radioButton102);
        RadioButton b103 = v.findViewById(R.id.radioButton103);
        RadioButton b104 = v.findViewById(R.id.radioButton104);
        if((b81.isChecked() || b82.isChecked() || b83.isChecked() || b84.isChecked())
                && (b101.isChecked() || b102.isChecked() || b103.isChecked()|| b104.isChecked())
                && (b81.isChecked() || b82.isChecked() || b83.isChecked()
                    || ((b91b.isChecked() || b92b.isChecked() || b93b.isChecked()|| b94b.isChecked())
                    && (b91p.isChecked() || b92p.isChecked() || b93p.isChecked()|| b94p.isChecked())
                    && (b91c.isChecked() || b92c.isChecked() || b93c.isChecked()|| b94c.isChecked())
                    && (b91fs.isChecked() || b92fs.isChecked() || b93fs.isChecked()|| b94fs.isChecked())))) {

            if (b81.isChecked()) { foodEmissionsKgs += 1000; }
            if (b82.isChecked()) { foodEmissionsKgs += 500; }
            if (b83.isChecked()) { foodEmissionsKgs += 1500; }
            if (b84.isChecked()) {
                if (b91b.isChecked()) { foodEmissionsKgs += 2500; }
                if (b92b.isChecked()) { foodEmissionsKgs += 1900; }
                if (b93b.isChecked()) { foodEmissionsKgs += 1300; }
                if (b91p.isChecked()) { foodEmissionsKgs += 1450; }
                if (b92p.isChecked()) { foodEmissionsKgs += 860; }
                if (b93p.isChecked()) { foodEmissionsKgs += 450; }
                if (b91c.isChecked()) { foodEmissionsKgs += 950; }
                if (b92c.isChecked()) { foodEmissionsKgs += 600; }
                if (b93c.isChecked()) { foodEmissionsKgs += 200; }
                if (b91fs.isChecked()) { foodEmissionsKgs += 800; }
                if (b92fs.isChecked()) { foodEmissionsKgs += 500; }
                if (b93fs.isChecked()) { foodEmissionsKgs += 150; }
            }
            if (b102.isChecked()) { foodEmissionsKgs += 23.4; }
            if (b103.isChecked()) { foodEmissionsKgs += 70.2; }
            if (b104.isChecked()) { foodEmissionsKgs += 140.2; }

            model.emissions().food().set(Mass.kg(foodEmissionsKgs));
            Log.d(TAG, "emissions: " + model.emissions());
            model.setScreen(OnboardingScreen.HOUSING);
        }  else {
            Toast.makeText(v.getContext(), "Please complete all the questions.", Toast.LENGTH_SHORT).show();
        }
    }
    public void NoMeat(View v) {
        // Make "No Meat" related views invisible
        v.findViewById(R.id.textView9).setVisibility(View.GONE);
        v.findViewById(R.id.textView9beef).setVisibility(View.GONE);
        v.findViewById(R.id.textView9pork).setVisibility(View.GONE);
        v.findViewById(R.id.textView9chkn).setVisibility(View.GONE);
        v.findViewById(R.id.textView9fs).setVisibility(View.GONE);
        v.findViewById(R.id.radioGroup9beef).setVisibility(View.GONE);
        v.findViewById(R.id.radioGroup9pork).setVisibility(View.GONE);
        v.findViewById(R.id.radioGroup9chkn).setVisibility(View.GONE);
        v.findViewById(R.id.radioGroup9fs).setVisibility(View.GONE);

        // Update the next question text
        TextView question10 = v.findViewById(R.id.textView10);
        question10.setText(R.string.survey_question10alt);

        // Ensure the order remains unchanged
        question10.setVisibility(View.VISIBLE);
    }
}
