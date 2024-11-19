package com.b07.planetze.onboarding;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;


public class QuestionsTransportationFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    float bruh = 0;

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
        TextView question4 = (TextView)view.findViewById(R.id.textView4);
        TextView question5 = (TextView)view.findViewById(R.id.textView5);
        TextView question6 = (TextView)view.findViewById(R.id.textView6);
        TextView question7 = (TextView)view.findViewById(R.id.textView7);
        buttonOwnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        Button buttonNotOwnCar = view.findViewById(R.id.radioButton12);
        buttonNotOwnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        Button buttonSubmit = view.findViewById(R.id.next);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsFoodFragment());

            }
        });

        //gathering data from radio buttons selected--------------------------

        RadioGroup answer1 = view.findViewById(R.id.radioGroup1);
        answer1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton11) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton12) {
                    bruh = 3;
                }
            }
        });
        RadioGroup answer2 = view.findViewById(R.id.radioGroup2);
        answer2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton21) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton22) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton23) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton24) {
                    bruh = 1;
                } else if (checkedId == R.id.radioButton25) {
                    bruh = 0;
                }
            }
        });
        RadioGroup answer3 = view.findViewById(R.id.radioGroup3);
        answer3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton31) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton32) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton33) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton34) {
                    bruh = 1;
                } else if (checkedId == R.id.radioButton35) {
                    bruh = 0;
                } else if (checkedId == R.id.radioButton36) {
                    bruh = 0;
                }
            }
        });
        RadioGroup answer4 = view.findViewById(R.id.radioGroup4);
        answer4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton41) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton42) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton43) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton44) {
                    bruh = 1;
                }
            }
        });
        RadioGroup answer5 = view.findViewById(R.id.radioGroup5);
        answer5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton51) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton52) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton53) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton54) {
                    bruh = 1;
                } else if (checkedId == R.id.radioButton55) {
                    bruh = 1;
                }
            }
        });
        RadioGroup answer6 = view.findViewById(R.id.radioGroup6);
        answer6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton61) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton62) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton63) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton64) {
                    bruh = 1;
                } else if (checkedId == R.id.radioButton65) {
                    bruh = 1;
                }
            }
        });
        RadioGroup answer7 = view.findViewById(R.id.radioGroup7);
        answer7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButton71) {
                    bruh = 4;
                } else if (checkedId == R.id.radioButton72) {
                    bruh = 3;
                } else if (checkedId == R.id.radioButton73) {
                    bruh = 2;
                } else if (checkedId == R.id.radioButton74) {
                    bruh = 1;
                } else if (checkedId == R.id.radioButton75) {
                    bruh = 1;
                }
            }
        });

        return view;
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //String country_selected = parent.getItemAtPosition(pos).toString();
        //System.out.println(country_selected);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
