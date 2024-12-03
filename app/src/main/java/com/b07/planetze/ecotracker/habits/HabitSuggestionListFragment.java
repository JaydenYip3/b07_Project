package com.b07.planetze.ecotracker.habits;

import static android.content.ContentValues.TAG;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.onboarding.CountryProcessor;
import com.b07.planetze.onboarding.QuestionsFoodFragment;
import com.b07.planetze.onboarding.QuestionsTransportationFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HabitSuggestionListFragment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    FirebaseDb db = new FirebaseDb();
    Emissions onBoarding;
    RecyclerView recyclerView;
    ArrayList<SuggestedHabit> habitList;
    MyAdapterSuggested myAdapter;
    String keyword = "";
    String filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_suggestion_list, container, false);

        ArrayAdapter<CharSequence> searchAdapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.habit_keywords,
                android.R.layout.simple_dropdown_item_1line
        );

        AutoCompleteTextView search = (AutoCompleteTextView) view.findViewById(R.id.search);
        search.setAdapter(searchAdapter);
        search.setOnItemClickListener(this);

        Spinner spinner = view.findViewById(R.id.filter);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.habit_filter,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        try {
            getData(view);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        /*Button buttonSubmit = view.findViewById(R.id.buttonNext);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(view);
            }
        });*/

        return view;
    }

    public void onSubmit(@NonNull View v) {
        //loadFragment(new QuestionsFoodFragment(emissions));
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getData(@NonNull View view) throws JSONException {
        HabitProcessor habitProcessor = new HabitProcessor(view.getContext(), "habits.json");
        habitList = new ArrayList<SuggestedHabit>();

        Log.d(TAG, "keyword"+keyword+"filter:"+filter);

        if(!Objects.equals(keyword, "")){
            for (int i = 1; i < 14; i++){
                Object[] habitInfo = habitProcessor.getResult("habit" + i);
                String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                    keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                }
                List keywordsList = Arrays.stream(keywords).toList();
                SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                if(keywordsList.contains(keyword)){
                    habitList.add(habit);
                }
            }
            keyword = "";
        }
        if(Objects.equals(filter, "None") && Objects.equals(keyword, "")){
            for (int i = 1; i < 14; i++){
                Object[] habitInfo = habitProcessor.getResult("habit" + i);

                String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                    keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                }
                SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                habitList.add(habit);
            }
            keyword = "";
        }

        if((Objects.equals(filter, "Category: Transportation")
                || Objects.equals(filter, "Category: Food")
                || Objects.equals(filter, "Category: Energy")
                || Objects.equals(filter, "Category: Consumption"))
                && Objects.equals(keyword, "")){
            for (int i = 1; i < 14; i++){
                Object[] habitInfo = habitProcessor.getResult("habit" + i);
                String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                    keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                }
                SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                String category = "Category: " + (String)habitInfo[2];
                if(Objects.equals(filter, category)){
                    habitList.add(habit);
                }
            }
            keyword = "";
        }
        if(Objects.equals(filter, "Impact") && Objects.equals(keyword, "")){
            for (int i = 1; i < 14; i++){
                Object[] habitInfo = habitProcessor.getResult("habit" + i);
                String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                    keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                }
                SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                habitList.add(habit);
            }
            Collections.sort(habitList);
            keyword = "";
        }

        // Filters by which category is the most emissions,
        // also filtering by impact within that category
        if(Objects.equals(filter, "Personalized Recommendations") && Objects.equals(keyword, "")){
            keyword = "";
            db.fetchOnboardingEmissions(result -> {
                result.match(emissionOption -> { // if this operation was successful:
                    emissionOption.match(// if the user has user info set:
                            onBoardingEmissions -> {
                                Log.d(TAG, "User found");
                                onBoarding = onBoardingEmissions;
                                String maxCat = "Transportation";
                                if(onBoarding.transport().kg() < onBoarding.food().kg()){
                                    maxCat = "Food";
                                }
                                if(onBoarding.energy().kg() > Math.max(onBoarding.transport().kg(),
                                        onBoarding.food().kg())){
                                    maxCat = "Energy";
                                }
                                if((onBoarding.shopping().kg() > Math.max(onBoarding.transport().kg(),
                                        onBoarding.food().kg())) && onBoarding.shopping().kg()
                                        > onBoarding.energy().kg()){
                                    maxCat = "Consumption";
                                }

                                for (int i = 1; i < 14; i++){
                                    Object[] habitInfo = habitProcessor.getResult("habit" + i);
                                    String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                                    for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                                        try {
                                            keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1],
                                            (String)habitInfo[2], (String)habitInfo[3],
                                            (String)habitInfo[4], (double)habitInfo[5],
                                            (String)habitInfo[6], keywords);
                                    if(Objects.equals((String)habitInfo[2], maxCat)){
                                        habitList.add(habit);
                                    }
                                }
                                Collections.sort(habitList);
                                myAdapter = new MyAdapterSuggested(this.getContext(), habitList);
                                recyclerView.setAdapter(myAdapter);
                                myAdapter.notifyDataSetChanged();
                            },
                            () -> Log.d(TAG, "user nonexistent"));
                }, dbError -> { // if this operation failed:
                    Log.d(TAG, "error: " + dbError);
                });
            });
        }

        if(!Objects.equals(filter, "Personalized Recommendations")){
            myAdapter = new MyAdapterSuggested(this.getContext(), habitList);
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
            //interface under onclick
        Log.d(TAG, "filter");
        filter = parent.getItemAtPosition(pos).toString();
        try {
            getData(view);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        keyword = parent.getItemAtPosition(position).toString();
        try {
            getData(view);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
