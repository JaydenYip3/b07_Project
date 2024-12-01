package com.b07.planetze.onboarding;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class QuestionsTransportationFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    FirebaseDb db = new FirebaseDb();
    Emissions transportEmissions;
    String country_selected;
    private double transportEmissionsKgs = 0;

    double[] emissions = new double[4];
    User currentUser;
    public LocalDate dateAdded= LocalDate.now();

    public QuestionsTransportationFragment(){
        emissions[0] = 0;
        emissions[1] = 0;
        emissions[2] = 0;
        emissions[3] = 0;
    }
    QuestionsTransportationFragment(double[] allEmissions){
        emissions[0] = allEmissions[0];
        emissions[1] = allEmissions[1];
        emissions[2] = allEmissions[2];
        emissions[3] = allEmissions[3];
    }
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
        RadioButton b11 = v.findViewById(R.id.radioButton11);
        RadioButton b12 = v.findViewById(R.id.radioButton12);
        RadioButton b21 = v.findViewById(R.id.radioButton21);
        RadioButton b22 = v.findViewById(R.id.radioButton22);
        RadioButton b23 = v.findViewById(R.id.radioButton23);
        RadioButton b24 = v.findViewById(R.id.radioButton24);
        RadioButton b25 = v.findViewById(R.id.radioButton25);
        RadioButton b31 = v.findViewById(R.id.radioButton31);
        RadioButton b32 = v.findViewById(R.id.radioButton32);
        RadioButton b33 = v.findViewById(R.id.radioButton33);
        RadioButton b34 = v.findViewById(R.id.radioButton34);
        RadioButton b35 = v.findViewById(R.id.radioButton35);
        RadioButton b36 = v.findViewById(R.id.radioButton36);
        RadioButton b41 = v.findViewById(R.id.radioButton41);
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
        RadioButton b65 = v.findViewById(R.id.radioButton65);
        RadioButton b71 = v.findViewById(R.id.radioButton71);
        RadioButton b72 = v.findViewById(R.id.radioButton72);
        RadioButton b73 = v.findViewById(R.id.radioButton73);
        RadioButton b74 = v.findViewById(R.id.radioButton74);
        RadioButton b75 = v.findViewById(R.id.radioButton75);

        if((b11.isChecked() || b12.isChecked())
                && (b41.isChecked() || b42.isChecked() || b43.isChecked() || b44.isChecked())
                && (b51.isChecked() || b52.isChecked() || b53.isChecked() || b54.isChecked() || b55.isChecked())
                && (b61.isChecked() || b62.isChecked() || b63.isChecked() || b64.isChecked() || b65.isChecked())
                && (b71.isChecked() || b72.isChecked() || b73.isChecked() || b74.isChecked() || b75.isChecked())
                && (b12.isChecked() || ((b21.isChecked() || b22.isChecked() || b23.isChecked() || b24.isChecked() || b25.isChecked())
                && b31.isChecked() || b32.isChecked() || b33.isChecked() || b34.isChecked() || b35.isChecked() || b36.isChecked()))) {

            if (b11.isChecked()) {
                if (b21.isChecked()) { transportEmissionsKgs += 0.24; }
                if (b22.isChecked()) { transportEmissionsKgs += 0.27; }
                if (b23.isChecked()) { transportEmissionsKgs += 0.16; }
                if (b24.isChecked()) { transportEmissionsKgs += 0.05; }
                if (b31.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 5000;
                if (b32.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 10000;
                if (b33.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 15000;
                if (b34.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 20000;
                if (b35.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 25000;
                if (b36.isChecked()) transportEmissionsKgs = transportEmissionsKgs * 35000;
            }
            if (b42.isChecked()) {
                if (b51.isChecked()) { transportEmissionsKgs += 246; }
                if (b52.isChecked()) { transportEmissionsKgs += 819; }
                if (b53.isChecked()) { transportEmissionsKgs += 1638; }
                if (b54.isChecked()) { transportEmissionsKgs += 3071; }
                if (b55.isChecked()) { transportEmissionsKgs += 4095; }
            }
            if (b43.isChecked() || b44.isChecked()) {
                if (b51.isChecked()) { transportEmissionsKgs += 573; }
                if (b52.isChecked()) { transportEmissionsKgs += 1911; }
                if (b53.isChecked()) { transportEmissionsKgs += 3822; }
                if (b54.isChecked()) { transportEmissionsKgs += 7166; }
                if (b55.isChecked()) { transportEmissionsKgs += 9555; }
            }
            if (b62.isChecked()) { transportEmissionsKgs += 225; }
            if (b63.isChecked()) { transportEmissionsKgs += 600; }
            if (b64.isChecked()) { transportEmissionsKgs += 1200; }
            if (b65.isChecked()) { transportEmissionsKgs += 1800; }
            if (b72.isChecked()) { transportEmissionsKgs += 825; }
            if (b73.isChecked()) { transportEmissionsKgs += 2200; }
            if (b74.isChecked()) { transportEmissionsKgs += 4400; }
            if (b75.isChecked()) { transportEmissionsKgs += 6600; }


            db.fetchUser(result -> {
                result.match(userOption -> { // if this operation was successful:
                    userOption.match(// if the user has user info set:
                            user -> {
                                Log.d(TAG, "User found");
                                currentUser = user;
                                Map<String, String> userJson = (HashMap<String, String>) currentUser.toJson();
                                userJson.put("country", country_selected);
                                User updatedUser = User.fromJson(userJson);
                                db.postUser(updatedUser, result1 -> {
                                    result1.match(user1 -> Log.d(TAG, "user found")
                                            , dbError -> { // if this operation failed:
                                                Log.d(TAG, "error: " + dbError);
                                            });
                                });
                            },
                            () -> Log.d(TAG, "user nonexistent"));
                }, dbError -> { // if this operation failed:
                    Log.d(TAG, "error: " + dbError);
                });
            });
            emissions[0] = transportEmissionsKgs;
            Log.d(TAG, "emissions: " + emissions[0] + emissions[1] + emissions[2] + emissions[3]);
            loadFragment(new QuestionsFoodFragment(emissions));
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        db.fetchUser(result -> {
            result.match(userOption -> { // if this operation was successful:
                userOption.match(// if the user has user info set:
                        user -> currentUser = user,
                        () -> Log.d(TAG, "user nonexistent"));
            }, dbError -> { // if this operation failed:
                Log.d(TAG, "error: " + dbError);
            });
        });

        country_selected = parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.onboarding_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
