package com.b07.planetze.ecotracker.habits;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.ecotracker.EcoTrackerHomeFragment;
import com.b07.planetze.onboarding.QuestionsFoodFragment;
import com.b07.planetze.onboarding.QuestionsTransportationFragment;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HabitSuggestionsFragment extends Fragment {
    FirebaseDb db = new FirebaseDb();
    User currentUser;
    public LocalDate dateAdded= LocalDate.now();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_suggestions, container, false);

        loadFragmentNested(new HabitsSelectedListFragment());

        Button buttonFrag1 = view.findViewById(R.id.buttonFrag1);
        Button buttonFrag2 = view.findViewById(R.id.buttonFrag2);
        View line1 = view.findViewById(R.id.line1);
        View line2 = view.findViewById(R.id.line2);
        buttonFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentNested(new HabitsSelectedListFragment());
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
            }

        });
        buttonFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentNested(new HabitSuggestionListFragment());
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
            }
        });

        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(EcoTrackerHomeFragment.newInstance()); //ecotracker home
            }
        });

        return view;
    }

    public void onSubmit(@NonNull View v) {
            //loadFragment(new QuestionsFoodFragment(emissions));
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ecotracker_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadFragmentNested(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
