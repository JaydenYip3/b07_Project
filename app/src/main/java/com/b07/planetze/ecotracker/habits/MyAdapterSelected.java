package com.b07.planetze.ecotracker.habits;

import static android.content.ContentValues.TAG;

import static com.b07.planetze.daily.food.MealDaily.MealType.FISH;
import static com.b07.planetze.daily.food.MealDaily.MealType.PLANT_BASED;
import static com.b07.planetze.daily.transport.DrivingDaily.VehicleType.ELECTRIC_CAR;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.b07.planetze.R;
import com.b07.planetze.common.Habit;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.daily.transport.DrivingDaily;
import com.b07.planetze.daily.transport.FlightDaily;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.result.Ok;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MyAdapterSelected extends RecyclerView.Adapter<MyAdapterSelected.MyViewHolder> {
    Context context;
    ArrayList<SuggestedHabit> habitList;
    FirebaseDb db = new FirebaseDb();


    public MyAdapterSelected(Context context, ArrayList<SuggestedHabit> habitList){
        this.context = context;
        this.habitList = habitList;
        Log.d(TAG, "adapter-habit size: " + habitList.size());
    }


    @NonNull
    @Override
    public MyAdapterSelected.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.selected_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSelected.MyViewHolder holder, int position) {
        SuggestedHabit habit = habitList.get(position);
        String habitDailyType = habit.dailyType;
        holder.header.setText(habit.header);
        holder.category.setText(habit.category);
        holder.description.setText(habit.description);
        holder.dailyType.setText(habit.dailyType);

        if(Objects.equals(habit.tracking, "frequency")){
            DataPoint d1 = null;
            DataPoint d2 = null;
            DataPoint d3 = null;
            DataPoint d4 = null;
            DataPoint d5 = null;

            d1 = new DataPoint(1, dailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(5), LocalDate.now().minusWeeks(4)), habit, db));
            d2 = new DataPoint(2, dailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(4), LocalDate.now().minusWeeks(3)), habit, db));
            d3 = new DataPoint(3, dailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(3), LocalDate.now().minusWeeks(2)), habit, db));
            d4 = new DataPoint(4, dailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(2), LocalDate.now().minusWeeks(1)), habit, db));
            d5 = new DataPoint(5, dailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(1), LocalDate.now()), habit, db));

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    // on below line we are adding
                    // each point on our x and y axis.
                    d1,
                    d2,
                    d3,
                    d4,
                    d5
            });
            GraphView graphView = holder.itemView.findViewById(R.id.graph);
            graphView.setTitle("Emissions Trend Graph");
            // on below line we are setting
            // text color to our graph view.
            graphView.setTitleColor(R.color.teal);
            // on below line we are setting
            // our title text size.
            graphView.setTitleTextSize(40);
            // on below line we are adding
            // data series to our graph view.
            graphView.addSeries(series);
        }

        boolean isVisible = habit.visibility;
        holder.constraintLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        boolean isSelected = habit.selected;
        if(!isSelected){
            holder.constraintLayout.setVisibility(View.GONE);
            holder.constraintLayoutWhole.setVisibility(View.GONE);
        }


    }
    public int dailyFreqInterval (DateInterval interval, SuggestedHabit habit, FirebaseDb database){
        final int[] count = {0};
        database.fetchDailies(interval, result -> {
            if (result instanceof Ok<DailyFetchList, ?> r) {
                DailyFetchList list = r.get();
                int incr = count[0];
                String targetDailyType = habit.dailyType;
                for (DailyFetch fetch : list) {
                    DailyType dailyType = fetch.daily().type();
                    if(targetDailyType == "DRIVING"){  //--------------------needs to account for per day, not per occurence
                        DrivingDaily drivingDaily = (DrivingDaily)fetch.daily();
                        if(drivingDaily.vehicleType() == ELECTRIC_CAR){
                            incr++;
                        }
                    }
                    else if(targetDailyType == "FLIGHT"){
                        FlightDaily flightDaily = (FlightDaily)fetch.daily();
                        if(flightDaily.numberFlights() < 2){
                            incr++;
                        }
                    }
                    else if(targetDailyType == "MEAL"){ //meal and energy bills require further distinction than dailytype
                        MealDaily mealDaily = (MealDaily)fetch.daily();
                        if(Objects.equals(habit.header, "Eat Less Meat")
                                && mealDaily.numberServings() <= 1){
                            incr++;
                        }
                        else if(Objects.equals(habit.header, "Adopt a Pescatarian Diet")
                                && mealDaily.mealType() == FISH){
                            incr++;
                        }
                        else if(Objects.equals(habit.header, "Adopt a Vegan/Vegetarian Diet")
                                && mealDaily.mealType() == PLANT_BASED){
                            incr++;
                        }
                    }

                    else if(((String) dailyType.toJson()).equals(targetDailyType)){
                        incr++;
                    }

                }
                count[0] = incr;
            }
        });
        return count[0];
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
        Button unselect;
        ConstraintLayout constraintLayout;
        ConstraintLayout constraintLayoutWhole;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            header = itemView.findViewById(R.id.header);
            category = itemView.findViewById(R.id.category);
            description = itemView.findViewById(R.id.description);
            dailyType = itemView.findViewById(R.id.daily);

            constraintLayout = itemView.findViewById(R.id.infoContainer);
            constraintLayoutWhole = itemView.findViewById(R.id.habit);
            unselect = itemView.findViewById(R.id.unselect);

            //onclick to expand
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "bru pos:" + getAdapterPosition());
                    if(getAdapterPosition()>-1) {
                        SuggestedHabit habit = habitList.get(getAdapterPosition());
                        habit.setVisibility(!habit.isVisibility());
                        notifyItemChanged(getAdapterPosition());
                    }
                }
            });

            unselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuggestedHabit habit = habitList.get(getAdapterPosition());

                    HabitProcessor habitProcessor = new HabitProcessor(itemView.getContext(), "habits.json");

                    Log.d(TAG, "bru pos:" + getAdapterPosition());
                    db.fetchHabit(result -> {
                        result.match(habitOption -> { // if this operation was successful:
                            habitOption.match(// if the user has user info set:
                                    habits -> {
                                        Log.d(TAG, "User found");
                                        ArrayList<String> userHabits = habits.keys();
                                        for (int i = 1; i < 14; i++){
                                            String key = "habit" + i;
                                            Object[] habitInfo = habitProcessor.getResult(key);
                                            if(userHabits.contains(key) && getAdapterPosition()>-1
                                                    && Objects.equals(habit.header, (String) habitInfo[0])){
                                                db.deleteHabit(key, result2 -> {
                                                    result2.match(user2 -> {
                                                            SuggestedHabit habitToDelete = habitList.get(getAdapterPosition());
                                                            habitToDelete.setSelected(false);
                                                            habitList.remove(getAdapterPosition());
                                                            notifyItemRemoved(getAdapterPosition());
                                                            notifyItemRangeChanged(getAdapterPosition(), habitList.size());
                                                            Log.d(TAG, "habitlist size: " + habitList.size());

                                                        }
                                                        , dbError -> { // if this operation failed:
                                                            Log.d(TAG, "error: " + dbError);
                                                        });
                                                });
                                            }
                                        }
                                    },
                                    () -> Log.d(TAG, "user nonexistent"));
                        }, dbError -> { // if this operation failed:
                            Log.d(TAG, "error: " + dbError);
                        });
                    });
                    Log.d(TAG, "del hab");
                    SuggestedHabit habitToDelete = habitList.get(getAdapterPosition());
                    habitToDelete.setSelected(!habitToDelete.isSelected());
                    habitList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), habitList.size());
                }
            });
        }
    }
}
