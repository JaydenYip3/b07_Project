package com.b07.planetze.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.b07.planetze.R;
import com.b07.planetze.util.measurement.Mass;

public class QuestionsTransportationFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    @NonNull private static final String TAG = "QuestionsTransportationFragment";
    private String country_selected;

    public QuestionsTransportationFragment() {}

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);

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

        buttonOwnCar.setOnClickListener(v -> ownCar(view));

        Button buttonNotOwnCar = view.findViewById(R.id.radioButton12);
        buttonNotOwnCar.setOnClickListener(v -> notOwnCar(view));

        Button buttonSubmit = view.findViewById(R.id.next);
        buttonSubmit.setOnClickListener(v -> onSubmit(view, model));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_questions_transportation, container, false);
    }

    public void ownCar(@NonNull View view) {
        // Make all the car-related views visible
        view.findViewById(R.id.textView2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView3).setVisibility(View.VISIBLE);
        view.findViewById(R.id.radioGroup3).setVisibility(View.VISIBLE);

        // Update the text for questions 4 to 7
        TextView question4 = view.findViewById(R.id.textView4);
        TextView question5 = view.findViewById(R.id.textView5);
        TextView question6 = view.findViewById(R.id.textView6);
        TextView question7 = view.findViewById(R.id.textView7);

        question4.setText(R.string.survey_question4);
        question5.setText(R.string.survey_question5);
        question6.setText(R.string.survey_question6);
        question7.setText(R.string.survey_question7);

        // Adjust question 4's position to appear below radioGroup3
        LinearLayout parentLayout = view.findViewById(R.id.parentLayout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) question4.getLayoutParams();

        layoutParams.topMargin = 16; // Add margin between question4 and radioGroup3
        question4.setLayoutParams(layoutParams);

        // Ensure question4 follows radioGroup3 in the layout hierarchy
        parentLayout.removeView(question4); // Remove it first
        parentLayout.addView(question4, parentLayout.indexOfChild(view.findViewById(R.id.radioGroup3)) + 1);
    }

    public void notOwnCar(@NonNull View view) {
        // Get references to TextViews for questions
        TextView question4 = view.findViewById(R.id.textView4);
        TextView question5 = view.findViewById(R.id.textView5);
        TextView question6 = view.findViewById(R.id.textView6);
        TextView question7 = view.findViewById(R.id.textView7);

        // Set text to alternate questions
        question4.setText(R.string.survey_question4alt);
        question5.setText(R.string.survey_question5alt);
        question6.setText(R.string.survey_question6alt);
        question7.setText(R.string.survey_question7alt);

        // Hide unrelated views
        view.findViewById(R.id.textView2).setVisibility(View.GONE);
        view.findViewById(R.id.radioGroup2).setVisibility(View.GONE);
        view.findViewById(R.id.textView3).setVisibility(View.GONE);
        view.findViewById(R.id.radioGroup3).setVisibility(View.GONE);

        // Adjust the position of question4
        LinearLayout parentLayout = view.findViewById(R.id.parentLayout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) question4.getLayoutParams();

        // Add margin between question4 and the previous element
        layoutParams.topMargin = 16;
        question4.setLayoutParams(layoutParams);

        // Ensure question4 follows radioGroup1 in the layout hierarchy
        parentLayout.removeView(question4); // Remove the view first
        parentLayout.addView(question4, parentLayout.indexOfChild(view.findViewById(R.id.radioGroup1)) + 1);
    }


    public void onSubmit(@NonNull View v, @NonNull OnboardingViewModel model) {
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
        Spinner spinner = v.findViewById(R.id.countries);

        if (spinner.getSelectedItem().toString().equals("***Select a Country***")){
            Toast.makeText(v.getContext(), "Please select a valid country", Toast.LENGTH_SHORT).show();
            return;
        }

        double transportEmissionsKg = 0;

        if((b11.isChecked() || b12.isChecked())
                && (b41.isChecked() || b42.isChecked() || b43.isChecked() || b44.isChecked())
                && (b51.isChecked() || b52.isChecked() || b53.isChecked() || b54.isChecked() || b55.isChecked())
                && (b61.isChecked() || b62.isChecked() || b63.isChecked() || b64.isChecked() || b65.isChecked())
                && (b71.isChecked() || b72.isChecked() || b73.isChecked() || b74.isChecked() || b75.isChecked())
                && (b12.isChecked() || ((b21.isChecked() || b22.isChecked() || b23.isChecked() || b24.isChecked() || b25.isChecked())
                && b31.isChecked() || b32.isChecked() || b33.isChecked() || b34.isChecked() || b35.isChecked() || b36.isChecked()))) {

            if (b11.isChecked()) {
                if (b21.isChecked()) { transportEmissionsKg += 0.24; }
                if (b22.isChecked()) { transportEmissionsKg += 0.27; }
                if (b23.isChecked()) { transportEmissionsKg += 0.16; }
                if (b24.isChecked()) { transportEmissionsKg += 0.05; }
                if (b31.isChecked()) transportEmissionsKg = transportEmissionsKg * 5000;
                if (b32.isChecked()) transportEmissionsKg = transportEmissionsKg * 10000;
                if (b33.isChecked()) transportEmissionsKg = transportEmissionsKg * 15000;
                if (b34.isChecked()) transportEmissionsKg = transportEmissionsKg * 20000;
                if (b35.isChecked()) transportEmissionsKg = transportEmissionsKg * 25000;
                if (b36.isChecked()) transportEmissionsKg = transportEmissionsKg * 35000;
            }
            if (b42.isChecked()) {
                if (b51.isChecked()) { transportEmissionsKg += 246; }
                if (b52.isChecked()) { transportEmissionsKg += 819; }
                if (b53.isChecked()) { transportEmissionsKg += 1638; }
                if (b54.isChecked()) { transportEmissionsKg += 3071; }
                if (b55.isChecked()) { transportEmissionsKg += 4095; }
            }
            if (b43.isChecked() || b44.isChecked()) {
                if (b51.isChecked()) { transportEmissionsKg += 573; }
                if (b52.isChecked()) { transportEmissionsKg += 1911; }
                if (b53.isChecked()) { transportEmissionsKg += 3822; }
                if (b54.isChecked()) { transportEmissionsKg += 7166; }
                if (b55.isChecked()) { transportEmissionsKg += 9555; }
            }
            if (b62.isChecked()) { transportEmissionsKg += 225; }
            if (b63.isChecked()) { transportEmissionsKg += 600; }
            if (b64.isChecked()) { transportEmissionsKg += 1200; }
            if (b65.isChecked()) { transportEmissionsKg += 1800; }
            if (b72.isChecked()) { transportEmissionsKg += 825; }
            if (b73.isChecked()) { transportEmissionsKg += 2200; }
            if (b74.isChecked()) { transportEmissionsKg += 4400; }
            if (b75.isChecked()) { transportEmissionsKg += 6600; }

            model.updateUserCountry(country_selected);
            model.emissions().transport().set(Mass.kg(transportEmissionsKg));

            Log.d(TAG, "emissions: " + model.emissions());
            model.setScreen(OnboardingScreen.FOOD);

        }  else {
            Toast.makeText(v.getContext(), "Please complete all the questions.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        country_selected = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
}
