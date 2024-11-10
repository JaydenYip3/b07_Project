package com.b07.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.auth.AuthActivity;
import com.b07.planetze.auth.AuthScreen;
import com.b07.planetze.auth.RegisterFragment;

public class WelcomeFragment extends Fragment{
    private static final String TAG = "WelcomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        View image = view.findViewById(R.id.imageView3);
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(30000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotate);

        buttonRegister.setOnClickListener(v -> AuthActivity.start(requireActivity(), AuthScreen.REGISTER));

        buttonLogin.setOnClickListener(v -> AuthActivity.start(requireActivity(), AuthScreen.LOGIN));

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
