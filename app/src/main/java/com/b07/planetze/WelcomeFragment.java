package com.b07.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.auth.AuthActivity;

public class WelcomeFragment extends Fragment{
    private static final String TAG = "WelcomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        /*
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RegisterFragment());        <- create register fragment/page
            }
        });
        */

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent();
            FragmentActivity activity = getActivity();
            if (activity == null) {
                Log.w(TAG, "getActivity() returned null");
                return;
            }
            intent.setClass(getActivity(), AuthActivity.class);
            activity.startActivity(intent);
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
