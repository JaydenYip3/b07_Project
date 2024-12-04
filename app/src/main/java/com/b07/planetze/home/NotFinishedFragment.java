package com.b07.planetze.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.b07.planetze.R;

public class NotFinishedFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_complete, container, false);

        ImageButton back = view.findViewById(R.id.ecogauge_back);


        back.setOnClickListener( v -> {
            getParentFragmentManager().popBackStack();
        });
        return view;
    }
}
