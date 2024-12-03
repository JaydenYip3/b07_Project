package com.b07.planetze.onboarding;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.measurement.Mass;

import java.time.LocalDate;

public class QuestionsHousingFragment extends Fragment{
    @NonNull private static final String TAG = "QuestionsHousingFragment";

    public QuestionsHousingFragment() {}

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);


        ImageButton buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> model.setScreen(OnboardingScreen.FOOD));

        Button buttonSubmit = view.findViewById(R.id.next);

        buttonSubmit.setOnClickListener(v -> onSubmit(view, model));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions_housing, container, false);
    }
    public void onSubmit(@NonNull View v, @NonNull OnboardingViewModel model) {
        String q11Answer = null;
        String q12Answer = null;
        String q13Answer = null;
        String q14Answer = null;
        String q15Answer = null;
        String q16Answer = null;

        RadioButton b111 = v.findViewById(R.id.radioButton111);
        RadioButton b112 = v.findViewById(R.id.radioButton112);
        RadioButton b113 = v.findViewById(R.id.radioButton113);
        RadioButton b114 = v.findViewById(R.id.radioButton114);
        RadioButton b115 = v.findViewById(R.id.radioButton115);
        RadioButton b121 = v.findViewById(R.id.radioButton121);
        RadioButton b122 = v.findViewById(R.id.radioButton122);
        RadioButton b123 = v.findViewById(R.id.radioButton123);
        RadioButton b124 = v.findViewById(R.id.radioButton124);
        RadioButton b131 = v.findViewById(R.id.radioButton131);
        RadioButton b132 = v.findViewById(R.id.radioButton132);
        RadioButton b133 = v.findViewById(R.id.radioButton133);
        RadioButton b141 = v.findViewById(R.id.radioButton141);
        RadioButton b142 = v.findViewById(R.id.radioButton142);
        RadioButton b143 = v.findViewById(R.id.radioButton143);
        RadioButton b144 = v.findViewById(R.id.radioButton144);
        RadioButton b145 = v.findViewById(R.id.radioButton145);
        RadioButton b146 = v.findViewById(R.id.radioButton146);
        RadioButton b151 = v.findViewById(R.id.radioButton151);
        RadioButton b152 = v.findViewById(R.id.radioButton152);
        RadioButton b153 = v.findViewById(R.id.radioButton153);
        RadioButton b154 = v.findViewById(R.id.radioButton154);
        RadioButton b155 = v.findViewById(R.id.radioButton155);
        RadioButton b161 = v.findViewById(R.id.radioButton161);
        RadioButton b162 = v.findViewById(R.id.radioButton162);
        RadioButton b163 = v.findViewById(R.id.radioButton163);
        RadioButton b164 = v.findViewById(R.id.radioButton164);
        RadioButton b165 = v.findViewById(R.id.radioButton165);
        RadioButton b166 = v.findViewById(R.id.radioButton166);
        RadioButton b171 = v.findViewById(R.id.radioButton171);
        RadioButton b172 = v.findViewById(R.id.radioButton172);
        RadioButton b173 = v.findViewById(R.id.radioButton173);
        if(b111.isChecked()) q11Answer = "A";
        if(b112.isChecked()) q11Answer = "B";
        if(b113.isChecked() || b115.isChecked()) q11Answer = "C";
        if(b114.isChecked()) q11Answer = "D";
        if(b121.isChecked()) q12Answer = "A";
        if(b122.isChecked()) q12Answer = "B";
        if(b123.isChecked()) q12Answer = "C";
        if(b124.isChecked()) q12Answer = "D";
        if(b131.isChecked()) q13Answer = "A";
        if(b132.isChecked()) q13Answer = "B";
        if(b133.isChecked()) q13Answer = "C";
        if(b141.isChecked()) q14Answer = "A";
        if(b142.isChecked()) q14Answer = "B";
        if(b143.isChecked()) q14Answer = "C";
        if(b144.isChecked()) q14Answer = "D";
        if(b145.isChecked()) q14Answer = "E";
        if(b146.isChecked()) q14Answer = "F";
        if(b151.isChecked()) q15Answer = "A";
        if(b152.isChecked()) q15Answer = "B";
        if(b153.isChecked()) q15Answer = "C";
        if(b154.isChecked()) q15Answer = "D";
        if(b155.isChecked()) q15Answer = "E";
        if(b161.isChecked()) q16Answer = "A";
        if(b162.isChecked()) q16Answer = "B";
        if(b163.isChecked()) q16Answer = "C";
        if(b164.isChecked()) q16Answer = "D";
        if(b165.isChecked()) q16Answer = "E";
        if(b166.isChecked()) q16Answer = "F";
        if (q11Answer!=null && q12Answer!=null && q13Answer!=null && q14Answer!=null && q15Answer!=null && q16Answer!=null
                && (b171.isChecked() || b172.isChecked() || b173.isChecked())) {

            String userKey = String.join("-", q11Answer, q12Answer, q13Answer, q14Answer, q15Answer);
            SurveyProcessor surveyProcessor = new SurveyProcessor(v.getContext(), "housing.json");
            double housingEmissionsKgs = surveyProcessor.getResult(userKey);
            if (q14Answer != null && q16Answer != null && !(q14Answer.equals(q16Answer))) {
                if (q16Answer.equals("E") || q16Answer.equals("B")) {
                    housingEmissionsKgs -= 233;
                } else {
                    housingEmissionsKgs += 233;
                }
            }
            if (b171.isChecked()) { housingEmissionsKgs -= 6000; }
            if (b172.isChecked()) { housingEmissionsKgs -= 4000; }

            model.emissions().energy().set(Mass.kg(housingEmissionsKgs));

            Log.d(TAG, "emissions: " + model.emissions());
            model.setScreen(OnboardingScreen.CONSUMPTION);
        } else {
            Toast.makeText(v.getContext(), "Please complete all the questions.", Toast.LENGTH_SHORT).show();
        }
    }
}
