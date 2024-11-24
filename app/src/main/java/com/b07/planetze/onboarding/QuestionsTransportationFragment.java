package com.b07.planetze.onboarding;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.Mass;
import com.b07.planetze.database.FakeDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;


@RequiresApi(api = Build.VERSION_CODES.O)
public class QuestionsTransportationFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userUid = user.getUid();
    FakeDatabase db = new FakeDatabase();
    Emissions transportEmissions = new Emissions();
    Mass transportMass = new Mass();
    Mass operandMass = Mass.fromKg(0);
    public LocalDate dateAdded= LocalDate.now();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_transportation, container, false);


        Spinner spinner = view.findViewById(R.id.countries);
    // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.country_arrays,
                android.R.layout.simple_spinner_item
        );
    // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //changing constraints (whether or not a question is showed) based off of answers --------------------------

        Button buttonOwnCar = view.findViewById(R.id.radioButton11);

        buttonOwnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownCar(view);

            }
        });

        Button buttonNotOwnCar = view.findViewById(R.id.radioButton12);
        buttonNotOwnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notOwnCar(view);
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

    public void ownCar(@NonNull View view) {
        TextView question4 = view.findViewById(R.id.textView4);
        TextView question5 = view.findViewById(R.id.textView5);
        TextView question6 = view.findViewById(R.id.textView6);
        TextView question7 = view.findViewById(R.id.textView7);
        view.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView3).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup3).setVisibility(View.VISIBLE);
        question4.setText(R.string.survey_question4);
        question5.setText(R.string.survey_question5);
        question6.setText(R.string.survey_question6);
        question7.setText(R.string.survey_question7);
        ConstraintLayout constraintLayout = view.findViewById(R.id.parentLayout);

        // Create a ConstraintSet and clone the current layout constraints
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Clear any existing constraints that might prevent repositioning
        constraintSet.clear(R.id.textView4, ConstraintSet.TOP);
        constraintSet.clear(R.id.textView4, ConstraintSet.START);
        constraintSet.clear(R.id.textView4, ConstraintSet.END);

        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView4, ConstraintSet.TOP, R.id.radioGroup3, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(R.id.textView4, ConstraintSet.START, R.id.radioGroup3, ConstraintSet.START, 0);
        constraintSet.connect(R.id.textView4, ConstraintSet.END, R.id.radioGroup3, ConstraintSet.END, 0);

        // Apply the new constraints to the layout
        constraintSet.applyTo(constraintLayout);
    }
    public void notOwnCar(@NonNull View view) {
        TextView question4 = view.findViewById(R.id.textView4);
        TextView question5 = view.findViewById(R.id.textView5);
        TextView question6 = view.findViewById(R.id.textView6);
        TextView question7 = view.findViewById(R.id.textView7);
        view.findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.radioGroup2).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.radioGroup3).setVisibility(View.INVISIBLE);
        question4.setText(R.string.survey_question4alt);
        question5.setText(R.string.survey_question5alt);
        question6.setText(R.string.survey_question6alt);
        question7.setText(R.string.survey_question7alt);

        ConstraintLayout constraintLayout = view.findViewById(R.id.parentLayout);

        // Create a ConstraintSet and clone the current layout constraints
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Clear any existing constraints that might prevent repositioning
        constraintSet.clear(R.id.textView4, ConstraintSet.TOP);
        constraintSet.clear(R.id.textView4, ConstraintSet.START);
        constraintSet.clear(R.id.textView4, ConstraintSet.END);

        // Set new constraints to move the TextView to the bottom-center of the parent
        constraintSet.connect(R.id.textView4, ConstraintSet.TOP, R.id.radioGroup1, ConstraintSet.BOTTOM, 16);
        constraintSet.connect(R.id.textView4, ConstraintSet.START, R.id.radioGroup1, ConstraintSet.START, 0);
        constraintSet.connect(R.id.textView4, ConstraintSet.END, R.id.radioGroup1, ConstraintSet.END, 0);

        // Apply the new constraints to the layout
        constraintSet.applyTo(constraintLayout);
    }

    public void onSubmit(@NonNull View v) {
        RadioButton b12 = v.findViewById(R.id.radioButton12);
        RadioButton b21 = v.findViewById(R.id.radioButton21);
        RadioButton b22 = v.findViewById(R.id.radioButton22);
        RadioButton b23 = v.findViewById(R.id.radioButton23);
        RadioButton b24 = v.findViewById(R.id.radioButton24);
        RadioButton b31 = v.findViewById(R.id.radioButton31);
        RadioButton b32 = v.findViewById(R.id.radioButton32);
        RadioButton b33 = v.findViewById(R.id.radioButton33);
        RadioButton b34 = v.findViewById(R.id.radioButton34);
        RadioButton b35 = v.findViewById(R.id.radioButton35);
        RadioButton b36 = v.findViewById(R.id.radioButton36);
        RadioButton b42 = v.findViewById(R.id.radioButton42);
        RadioButton b43 = v.findViewById(R.id.radioButton43);
        RadioButton b44 = v.findViewById(R.id.radioButton44);
        RadioButton b51 = v.findViewById(R.id.radioButton51);
        RadioButton b52 = v.findViewById(R.id.radioButton52);
        RadioButton b53 = v.findViewById(R.id.radioButton53);
        RadioButton b54 = v.findViewById(R.id.radioButton54);
        RadioButton b55 = v.findViewById(R.id.radioButton55);
        RadioButton b61 = v.findViewById(R.id.radioButton61);
        RadioButton b62 = v.findViewById(R.id.radioButton62);
        RadioButton b63 = v.findViewById(R.id.radioButton63);
        RadioButton b64 = v.findViewById(R.id.radioButton64);
        RadioButton b71 = v.findViewById(R.id.radioButton71);
        RadioButton b72 = v.findViewById(R.id.radioButton72);
        RadioButton b73 = v.findViewById(R.id.radioButton73);
        RadioButton b74 = v.findViewById(R.id.radioButton74);
        if(b12.isChecked()){
            if(b21.isChecked()){
                operandMass.setKg(0.24);
                transportMass.add(operandMass);
            }
            if(b22.isChecked()){
                operandMass.setKg(0.27);
                transportMass.add(operandMass);
            }
            if(b23.isChecked()){
                operandMass.setKg(0.16);
                transportMass.add(operandMass);
            }
            if(b24.isChecked()){
                operandMass.setKg(0.05);
                transportMass.add(operandMass);
            }
        }
        if(b31.isChecked()) transportMass.scale(5000);
        if(b32.isChecked()) transportMass.scale(10000);
        if(b33.isChecked()) transportMass.scale(15000);
        if(b34.isChecked()) transportMass.scale(20000);
        if(b35.isChecked()) transportMass.scale(25000);
        if(b36.isChecked()) transportMass.scale(35000);
        if(b42.isChecked()){
            if(b51.isChecked()){
                operandMass.setKg(246);
                transportMass.add(operandMass);
            }
            if(b52.isChecked()){
                operandMass.setKg(819);
                transportMass.add(operandMass);
            }
            if(b53.isChecked()){
                operandMass.setKg(1638);
                transportMass.add(operandMass);
            }
            if(b54.isChecked()){
                operandMass.setKg(3071);
                transportMass.add(operandMass);
            }
            if(b55.isChecked()){
                operandMass.setKg(4095);
                transportMass.add(operandMass);
            }
        }
        if(b43.isChecked() || b44.isChecked()){
            if(b51.isChecked()){
                operandMass.setKg(573);
                transportMass.add(operandMass);
            }
            if(b52.isChecked()){
                operandMass.setKg(1911);
                transportMass.add(operandMass);
            }
            if(b53.isChecked()){
                operandMass.setKg(3822);
                transportMass.add(operandMass);
            }
            if(b54.isChecked()){
                operandMass.setKg(7166);
                transportMass.add(operandMass);
            }
            if(b55.isChecked()){
                operandMass.setKg(9555);
                transportMass.add(operandMass);
            }
        }
        if(b61.isChecked()){
            operandMass.setKg(225);
            transportMass.add(operandMass);
        }
        if(b62.isChecked()){
            operandMass.setKg(600);
            transportMass.add(operandMass);
        }
        if(b63.isChecked()){
            operandMass.setKg(1200);
            transportMass.add(operandMass);
        }
        if(b64.isChecked()){
            operandMass.setKg(1800);
            transportMass.add(operandMass);
        }
        if(b71.isChecked()){
            operandMass.setKg(825);
            transportMass.add(operandMass);
        }
        if(b72.isChecked()){
            operandMass.setKg(2200);
            transportMass.add(operandMass);
        }
        if(b73.isChecked()){
            operandMass.setKg(4400);
            transportMass.add(operandMass);
        }
        if(b74.isChecked()){
            operandMass.setKg(6600);
            transportMass.add(operandMass);
        }

        operandMass = transportEmissions.transportation();
        operandMass.set(transportMass);
        /*db.updateDailyEmissions(userUid, dateAdded, transportEmissions, result -> {
 *         result.match(unit -> {
 *             Log.d(TAG, unit.toString());
 *         }, error -> {
 *             Log.e(TAG, error.message());
 *         });
 *     });*/
        loadFragment(new QuestionsFoodFragment());
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //String country_selected = parent.getItemAtPosition(pos).toString();
        //System.out.println(country_selected);

        //update country/region field of user in database!
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
