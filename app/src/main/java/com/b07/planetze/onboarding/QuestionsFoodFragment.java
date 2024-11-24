package com.b07.planetze.onboarding;

import static android.view.View.INVISIBLE;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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
public class QuestionsFoodFragment extends Fragment{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userUid = user.getUid();
    FakeDatabase db = new FakeDatabase();
    Emissions foodEmissions = new Emissions();
    Mass foodMass = new Mass();
    Mass operandMass = Mass.fromKg(0);
    public LocalDate dateAdded= LocalDate.now();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_food, container, false);

        Button buttonMeat = view.findViewById(R.id.radioButton84);
        buttonMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eatMeat(view);
            }
        });



        Button buttonNoMeat1 = view.findViewById(R.id.radioButton81);
        Button buttonNoMeat2 = view.findViewById(R.id.radioButton82);
        Button buttonNoMeat3 = view.findViewById(R.id.radioButton83);

        buttonNoMeat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoMeat(view);
            }
        });
        buttonNoMeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoMeat(view);
            }
        });
        buttonNoMeat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoMeat(view);
            }
        });


        Button buttonBack = view.findViewById(R.id.back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsTransportationFragment());
            }
        });

        Button buttonNext = view.findViewById(R.id.next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(view);
            }
        });

        return view;
    }

    public void eatMeat(View view){
        TextView question10 = (TextView)view.findViewById(R.id.textView10);
        view.findViewById(R.id.textView9).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9beef).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9pork).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9chkn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView9fs).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9beef).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9pork).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9chkn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup9fs).setVisibility(View.VISIBLE);
        question10.setText(R.string.survey_question10);
        ConstraintLayout constraintLayout = view.findViewById(R.id.parentLayout);

        // Create a ConstraintSet and clone the current layout constraints
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Clear any existing constraints that might prevent repositioning
        constraintSet.clear(R.id.textView10, ConstraintSet.TOP);
        constraintSet.clear(R.id.textView10, ConstraintSet.START);
        constraintSet.clear(R.id.textView10, ConstraintSet.END);

        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView10, ConstraintSet.TOP, R.id.radioGroup9fs, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(R.id.textView10, ConstraintSet.START, R.id.radioGroup9fs, ConstraintSet.START, 0);
        constraintSet.connect(R.id.textView10, ConstraintSet.END, R.id.radioGroup9fs, ConstraintSet.END, 0);

        //constraintSet.clear(R.id.textView9chkn, ConstraintSet.TOP);
        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView9chkn, ConstraintSet.TOP, R.id.radioGroup9pork, ConstraintSet.BOTTOM, 16);
        // Apply the new constraints to the layout

        //constraintSet.clear(R.id.textView9, ConstraintSet.TOP);
        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView9, ConstraintSet.TOP, R.id.radioGroup8, ConstraintSet.BOTTOM, 16);
        // Apply the new constraints to the layout

        constraintSet.applyTo(constraintLayout);
    }
    public void onSubmit(View v) {
        RadioButton b81 = v.findViewById(R.id.radioButton81);
        RadioButton b82 = v.findViewById(R.id.radioButton82);
        RadioButton b83 = v.findViewById(R.id.radioButton83);
        RadioButton b84 = v.findViewById(R.id.radioButton84);
        RadioButton b91b = v.findViewById(R.id.radioButton91beef);
        RadioButton b92b = v.findViewById(R.id.radioButton92beef);
        RadioButton b93b = v.findViewById(R.id.radioButton93beef);
        RadioButton b91p = v.findViewById(R.id.radioButton91pork);
        RadioButton b92p = v.findViewById(R.id.radioButton92pork);
        RadioButton b93p = v.findViewById(R.id.radioButton93pork);
        RadioButton b91c = v.findViewById(R.id.radioButton91chkn);
        RadioButton b92c = v.findViewById(R.id.radioButton92chkn);
        RadioButton b93c = v.findViewById(R.id.radioButton93chkn);
        RadioButton b91fs = v.findViewById(R.id.radioButton91fs);
        RadioButton b92fs = v.findViewById(R.id.radioButton92fs);
        RadioButton b93fs = v.findViewById(R.id.radioButton93fs);
        RadioButton b102 = v.findViewById(R.id.radioButton102);
        RadioButton b103 = v.findViewById(R.id.radioButton103);
        RadioButton b104 = v.findViewById(R.id.radioButton104);
        if(b81.isChecked()){
            operandMass.setKg(1000);
            foodMass.add(operandMass);
        }
        if(b82.isChecked()){
            operandMass.setKg(500);
            foodMass.add(operandMass);
        }
        if(b83.isChecked()){
            operandMass.setKg(1500);
            foodMass.add(operandMass);
        }
        if(b84.isChecked()){
            if(b91b.isChecked()){
                operandMass.setKg(2500);
                foodMass.add(operandMass);
            }
            if(b92b.isChecked()){
                operandMass.setKg(1900);
                foodMass.add(operandMass);
            }
            if(b93b.isChecked()){
                operandMass.setKg(1300);
                foodMass.add(operandMass);
            }
            if(b91p.isChecked()){
                operandMass.setKg(1450);
                foodMass.add(operandMass);
            }
            if(b92p.isChecked()){
                operandMass.setKg(860);
                foodMass.add(operandMass);
            }
            if(b93p.isChecked()){
                operandMass.setKg(450);
                foodMass.add(operandMass);
            }
            if(b91c.isChecked()){
                operandMass.setKg(950);
                foodMass.add(operandMass);
            }
            if(b92c.isChecked()){
                operandMass.setKg(600);
                foodMass.add(operandMass);
            }
            if(b93c.isChecked()){
                operandMass.setKg(200);
                foodMass.add(operandMass);
            }
            if(b91fs.isChecked()){
                operandMass.setKg(800);
                foodMass.add(operandMass);
            }
            if(b92fs.isChecked()){
                operandMass.setKg(500);
                foodMass.add(operandMass);
            }
            if(b93fs.isChecked()){
                operandMass.setKg(150);
                foodMass.add(operandMass);
            }
        }
        if(b102.isChecked()){
            operandMass.setKg(23.4);
            foodMass.add(operandMass);
        }
        if(b103.isChecked()){
            operandMass.setKg(70.2);
            foodMass.add(operandMass);
        }
        if(b104.isChecked()){
            operandMass.setKg(140.4);
            foodMass.add(operandMass);
        }
        operandMass = foodEmissions.transportation();
        operandMass.set(foodMass);
        loadFragment(new QuestionsHousingFragment());
    }
    public void NoMeat(@NonNull View v) {

        TextView question10 = (TextView)v.findViewById(R.id.textView10);
        v.findViewById(R.id.textView9).setVisibility(INVISIBLE);
        v.findViewById(R.id.textView9beef).setVisibility(INVISIBLE);
        v.findViewById(R.id.textView9pork).setVisibility(INVISIBLE);
        v.findViewById(R.id.textView9chkn).setVisibility(INVISIBLE);
        v.findViewById(R.id.textView9fs).setVisibility(INVISIBLE);
        v.findViewById(R.id.radioGroup9beef).setVisibility(INVISIBLE);
        v.findViewById(R.id.radioGroup9pork).setVisibility(INVISIBLE);
        v.findViewById(R.id.radioGroup9chkn).setVisibility(INVISIBLE);
        v.findViewById(R.id.radioGroup9fs).setVisibility(INVISIBLE);
        question10.setText(R.string.survey_question10alt);
        ConstraintLayout constraintLayout = v.findViewById(R.id.parentLayout);

        // Create a ConstraintSet and clone the current layout constraints
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Clear any existing constraints that might prevent repositioning
        constraintSet.clear(R.id.textView10, ConstraintSet.TOP);
        constraintSet.clear(R.id.textView10, ConstraintSet.START);
        constraintSet.clear(R.id.textView10, ConstraintSet.END);

        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView10, ConstraintSet.TOP, R.id.radioGroup8, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(R.id.textView10, ConstraintSet.START, R.id.radioGroup8, ConstraintSet.START, 0);
        constraintSet.connect(R.id.textView10, ConstraintSet.END, R.id.radioGroup8, ConstraintSet.END, 0);

        //constraintSet.clear(R.id.textView9chkn, ConstraintSet.TOP);
        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView9chkn, ConstraintSet.TOP, R.id.parentLayout, ConstraintSet.TOP, 16);
        // Apply the new constraints to the layout

        //constraintSet.clear(R.id.textView9, ConstraintSet.TOP);
        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView9, ConstraintSet.TOP, R.id.parentLayout, ConstraintSet.TOP, 16);
        // Apply the new constraints to the layout

        constraintSet.applyTo(constraintLayout);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
