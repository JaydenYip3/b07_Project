package com.b07.planetze.ecotracker.habits;

import static android.content.ContentValues.TAG;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.database.firebase.FirebaseDb;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class HabitsSelectedListFragment extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    FirebaseDb db = new FirebaseDb();
    Emissions onBoarding;
    public LocalDate dateAdded= LocalDate.now();
    RecyclerView recyclerView;
    ArrayList<SuggestedHabit> habitList;
    MyAdapterSelected myAdapter;
    String keyword = "";
    String filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits_selected_list, container, false);

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
                R.array.habit_selected_filter,
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

        //getData(view);

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getData(View view) {
        HabitProcessor habitProcessor = new HabitProcessor(view.getContext(), "habits.json");
        habitList = new ArrayList<SuggestedHabit>();
        db.fetchHabit(result -> {
            result.match(habitOption -> { // if this operation was successful:
                habitOption.match(// if the user has user info set:
                        habits -> {
                            Map<String, ArrayList<String>> userJson = (Map<String, ArrayList<String>>) habits.toJson();
                            ArrayList<String> userHabits = userJson.get("keys");
                            if(userHabits!=null && !Objects.equals(keyword, "")){
                                for (int i = 1; i < 14; i++){
                                    String key = "habit" + i;
                                    Object[] habitInfo = habitProcessor.getResult(key);
                                    String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                                    for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                                        try {
                                            keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    List<String> keywordsList = Arrays.stream(keywords).toList();
                                    if(keywordsList.contains(keyword) && userHabits.contains(key)){
                                        SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                                        habitList.add(habit);
                                    }
                                }
                                keyword="";
                            }
                            else if(userHabits!=null && Objects.equals(filter, "None")){
                                Log.d(TAG, "None filter");
                                for (int i = 1; i < 14; i++){
                                    String key = "habit" + i;
                                    Object[] habitInfo = habitProcessor.getResult(key);
                                    String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                                    for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                                        try {
                                            keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    if(userHabits.contains(key)){
                                        SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                                        habitList.add(habit);
                                    }
                                }
                                keyword = "";
                            }
                            else if(userHabits!=null &&
                                    (Objects.equals(filter, "Category: Transportation" )
                                    || Objects.equals(filter, "Category: Food")
                                    || Objects.equals(filter, "Category: Housing")
                                    || Objects.equals(filter, "Category: Consumption"))){
                                for (int i = 1; i < 14; i++){
                                    String key = "habit" + i;
                                    Object[] habitInfo = habitProcessor.getResult(key);
                                    String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                                    for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                                        try {
                                            keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    if(userHabits.contains(key)){
                                        SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                                        String category = "Category: " + (String)habitInfo[2];
                                        if(Objects.equals(filter, category)){
                                            habitList.add(habit);
                                        }
                                    }
                                }
                                keyword = "";
                            }
                            else if(userHabits!=null && Objects.equals(filter, "Impact")){
                                for (int i = 1; i < 14; i++){
                                    String key = "habit" + i;
                                    Object[] habitInfo = habitProcessor.getResult(key);
                                    String[] keywords = new String[((JSONArray)habitInfo[7]).length()];
                                    for (int j = 0; j < ((JSONArray)habitInfo[7]).length(); j++){
                                        try {
                                            keywords[j] = ((JSONArray)habitInfo[7]).getString(j);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    if(userHabits.contains(key)){
                                        SuggestedHabit habit = new SuggestedHabit((String)habitInfo[0], (String)habitInfo[1], (String)habitInfo[2], (String)habitInfo[3], (String)habitInfo[4], (double)habitInfo[5], (String)habitInfo[6], keywords);
                                        habitList.add(habit);
                                        Collections.sort(habitList);
                                    }
                                }
                                keyword = "";
                            }
                            myAdapter = new MyAdapterSelected(this.getContext(), habitList);
                            recyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();

                        },
                        () -> Log.d(TAG, "user nonexistent"));
            }, dbError -> { // if this operation failed:
                Log.d(TAG, "error: " + dbError);
            });
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        filter = parent.getItemAtPosition(pos).toString();
        if(view!=null){
            getData(view);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        keyword = parent.getItemAtPosition(position).toString();
        getData(view);
    }
}
