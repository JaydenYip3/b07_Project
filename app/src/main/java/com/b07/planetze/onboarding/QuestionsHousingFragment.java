package com.b07.planetze.onboarding;

import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.Mass;
import com.b07.planetze.database.FakeDatabase;

import java.time.LocalDate;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class QuestionsHousingFragment extends Fragment{
    FakeDatabase db = new FakeDatabase();
    Emissions housingEmissions = new Emissions();
    Mass housingMass = new Mass();
    Mass operandMass = Mass.fromKg(0);
    public LocalDate dateAdded= LocalDate.now();

    String q11Answer;
    String q12Answer;
    String q13Answer;
    String q14Answer;
    String q15Answer;
    String q16Answer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_housing, container, false);


        Button buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsFoodFragment());
            }
        });


        Button buttonSubmit = view.findViewById(R.id.next);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(view);
            }
        });

        return view;
    }
    public void onSubmit(@NonNull View v) {
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

        String userKey = String.join("-", q11Answer, q12Answer, q13Answer, q14Answer, q15Answer);
        SurveyProcessor surveyProcessor = new SurveyProcessor(v.getContext(), "housing.json");
        int result = surveyProcessor.getResult(userKey);
        operandMass.setKg(result);
        housingMass.add(operandMass);
        if(!(q14Answer.equals(q16Answer))){
            if(q16Answer.equals("E") || q16Answer.equals("B")){
                operandMass.setKg(233);
                housingMass.subtract(operandMass);
            }
            else{
                operandMass.setKg(233);
                housingMass.add(operandMass);
            }
        }
        if(b171.isChecked()){
            operandMass.setKg(6000);
            housingMass.subtract(operandMass);
        }
        operandMass = housingEmissions.transportation();
        operandMass.set(housingMass);
        loadFragment(new QuestionsConsumptionFragment());
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
