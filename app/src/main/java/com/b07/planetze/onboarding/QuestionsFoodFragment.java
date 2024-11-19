package com.b07.planetze.onboarding;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.WelcomeFragment;

public class QuestionsFoodFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_food, container, false);

        Button buttonMeat = view.findViewById(R.id.radioButton84);
        TextView question10 = (TextView)view.findViewById(R.id.textView10);
        buttonMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsTransportationFragment());
            }
        });

        Button buttonSubmit = view.findViewById(R.id.next);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsHousingFragment());
            }
        });

        return view;
    }
    public void NoMeat(View v) {

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
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
