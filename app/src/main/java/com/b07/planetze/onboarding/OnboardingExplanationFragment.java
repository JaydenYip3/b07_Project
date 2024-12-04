package com.b07.planetze.onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.b07.planetze.R;

public class OnboardingExplanationFragment extends Fragment {


    public OnboardingExplanationFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        var model = new ViewModelProvider(requireActivity())
                .get(OnboardingViewModel.class);

        Button go = view.findViewById(R.id.onboarding_go);
        go.setOnClickListener(v -> {
            model.setScreen(OnboardingScreen.TRANSPORTATION);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding_explanation, container, false);
    }
}