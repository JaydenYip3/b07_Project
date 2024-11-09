package com.b07.planetze;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuestionsFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        Button buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new WelcomeFragment());
            }
        });

        Button buttonSubmit = view.findViewById(R.id.submit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new WelcomeFragment());      //  <- create calculation fragment/page
            }
        });

        View scrollView = view.findViewById(R.id.scrollView);
        TextView categoryTextView = view.findViewById(R.id.textViewCat);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY<1100){
                    categoryTextView.setText(R.string.survey_category1);
                }
                if(scrollY>1100){
                    categoryTextView.setText(R.string.survey_category2);
                }
                if(scrollY>2650){
                    categoryTextView.setText(R.string.survey_category3);
                }
                if(scrollY>4225){
                    categoryTextView.setText(R.string.survey_category4);
                }
                if(scrollY>6175){
                    categoryTextView.setText(R.string.survey_category5);
                }
                if(scrollY>11025){
                    categoryTextView.setText(R.string.survey_category6);
                }
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
