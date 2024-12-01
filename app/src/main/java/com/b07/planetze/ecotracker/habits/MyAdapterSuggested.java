package com.b07.planetze.ecotracker.habits;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.b07.planetze.R;
import com.b07.planetze.common.Habit;
import com.b07.planetze.database.firebase.FirebaseDb;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyAdapterSuggested extends RecyclerView.Adapter<MyAdapterSuggested.MyViewHolder> {
    Context context;
    ArrayList<SuggestedHabit> habitList;
    FirebaseDb db = new FirebaseDb();

    public MyAdapterSuggested(Context context, ArrayList<SuggestedHabit> habitList){
        this.context = context;
        this.habitList = habitList;
    }


    @NonNull
    @Override
    public MyAdapterSuggested.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.suggested_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSuggested.MyViewHolder holder, int position) {
        SuggestedHabit habit = habitList.get(position);
        holder.header.setText(habit.header);
        holder.category.setText(habit.category);
        holder.description.setText(habit.description);

        boolean isVisible = habit.visibility;
        holder.constraintLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        TextView dailyType;
        TextView category;
        TextView description;
        Button select;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            header = itemView.findViewById(R.id.header);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.description);
            constraintLayout = itemView.findViewById(R.id.infoContainer);
            select = itemView.findViewById(R.id.select);

            //onclick to expand
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuggestedHabit habit = habitList.get(getAdapterPosition());
                    habit.setVisibility(!habit.isVisibility());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            //onclick to select habit, add to user
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "pos: "+getAdapterPosition());
                    SuggestedHabit habit;
                    habit = habitList.get(getAdapterPosition());

                    HabitProcessor habitProcessor = new HabitProcessor(itemView.getContext(), "habits.json");

                    db.fetchHabit(result -> {
                        result.match(habitOption -> { // if this operation was successful:
                            habitOption.match(// if the user has user info set:
                                    habits -> {
                                        Log.d(TAG, "User found");
                                        Map<String, ArrayList<String>> userJson = (Map<String, ArrayList<String>>) habits.toJson();
                                        ArrayList<String> userHabits = userJson.get("keys");
                                            for (int i = 1; i < 14; i++){
                                                String key = "habit" + i;
                                                Object[] habitInfo = habitProcessor.getResult(key);
                                                if(userHabits!=null && userHabits.contains(key)
                                                        && Objects.equals(habit.header, (String) habitInfo[0])){
                                                    db.deleteHabit(key, result2 -> {
                                                        result2.match(user2 -> Log.d(TAG, "user found")
                                                                , dbError -> { // if this operation failed:
                                                                    Log.d(TAG, "error: " + dbError);
                                                                });
                                                    });
                                                    select.setText("Select");
                                                }
                                                if(Objects.equals(habit.header, (String) habitInfo[0])
                                                        && !userHabits.contains(key)){
                                                    db.postHabit(key, result2 -> {
                                                        result2.match(user -> Log.d(TAG, "user found")
                                                                , dbError -> { // if this operation failed:
                                                                    Log.d(TAG, "error: " + dbError);
                                                                });
                                                    });
                                                    select.setText("Unselect");
                                                }
                                            }
                                    },
                                    () -> {
                                        for (int i = 1; i < 14; i++) {
                                            String key2 = "habit" + i;
                                            Object[] habitInfo = habitProcessor.getResult(key2);
                                            if(Objects.equals(habit.header, (String) habitInfo[0])){
                                                db.postHabit(key2, result2 -> {
                                                    result2.match(user -> Log.d(TAG, "user found")
                                                            , dbError -> { // if this operation failed:
                                                                Log.d(TAG, "error: " + dbError);
                                                            });
                                                });
                                                select.setText("Unselect");
                                            }
                                        }
                                    });
                        }, dbError -> { // if this operation failed:
                            Log.d(TAG, "error: " + dbError);
                        });
                    });
                }
            });
        }
    }
}
