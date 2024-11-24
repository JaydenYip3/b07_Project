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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
@RequiresApi(api = Build.VERSION_CODES.O)
public class QuestionsConsumptionFragment extends Fragment{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userUid = user.getUid();
    FakeDatabase db = new FakeDatabase();
    Emissions consumptionEmissions = new Emissions();
    Mass consumptionMass = new Mass();
    Mass operandMass = Mass.fromKg(0);
    public LocalDate dateAdded= LocalDate.now();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_consumption, container, false);


        Button buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsHousingFragment());
            }
        });


        Button buttonSubmit = view.findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(view);
            }
        });

        return view;
    }

    public void onSubmit(@NonNull View v) {
        RadioButton b181 = v.findViewById(R.id.radioButton181);
        RadioButton b182 = v.findViewById(R.id.radioButton182);
        RadioButton b183 = v.findViewById(R.id.radioButton183);
        RadioButton b184 = v.findViewById(R.id.radioButton184);
        RadioButton b191 = v.findViewById(R.id.radioButton191);
        RadioButton b192 = v.findViewById(R.id.radioButton192);
        RadioButton b202 = v.findViewById(R.id.radioButton202);
        RadioButton b203 = v.findViewById(R.id.radioButton203);
        RadioButton b204 = v.findViewById(R.id.radioButton204);
        RadioButton b205 = v.findViewById(R.id.radioButton205);
        RadioButton b212 = v.findViewById(R.id.radioButton212);
        RadioButton b213 = v.findViewById(R.id.radioButton213);
        RadioButton b214 = v.findViewById(R.id.radioButton214);
        if (b181.isChecked()) {
            operandMass.setKg(360);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(54);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(108);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(180);
                consumptionMass.add(operandMass);
            }
        }
        if (b182.isChecked()) {
            operandMass.setKg(120);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(18);      //based on annual (100) * 1.2 = 120 (quarterly)
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(36);      //based on annual (100) * 1.2 = 120 (quarterly)
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(60);      //based on annual (100) * 1.2 = 120 (quarterly)
                consumptionMass.add(operandMass);
            }
        }
        if (b183.isChecked()) {
            operandMass.setKg(100);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(15);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(30);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(50);
                consumptionMass.add(operandMass);
            }
        }
        if (b184.isChecked()) {
            operandMass.setKg(5);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(0.75);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(1.5);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(2.5);
                consumptionMass.add(operandMass);
            }
        }
        if (b202.isChecked()) {
            operandMass.setKg(300);
            consumptionMass.add(operandMass);
            if (b212.isChecked()) {
                operandMass.setKg(45);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(60);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(90);
                consumptionMass.add(operandMass);
            }
        }
        if (b203.isChecked()) {
            operandMass.setKg(600);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(60);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(120);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(180);
                consumptionMass.add(operandMass);
            }
        }
        if (b204.isChecked()) {
            operandMass.setKg(900);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(90);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(180);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(270);
                consumptionMass.add(operandMass);
            }
        }
        if (b205.isChecked()) {
            operandMass.setKg(1200);
            consumptionMass.add(operandMass);
            if (b191.isChecked()) consumptionMass.scale(0.5);
            if (b192.isChecked()) consumptionMass.scale(0.3);
            if (b212.isChecked()) {
                operandMass.setKg(120);
                consumptionMass.add(operandMass);
            }
            if (b213.isChecked()) {
                operandMass.setKg(240);
                consumptionMass.add(operandMass);
            }
            if (b214.isChecked()) {
                operandMass.setKg(360);
                consumptionMass.add(operandMass);
            }
        }
        operandMass = consumptionEmissions.transportation();
        operandMass.set(consumptionMass);
        loadFragment(new CalcDisplayFragment());

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
