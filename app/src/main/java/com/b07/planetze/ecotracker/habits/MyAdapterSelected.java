package com.b07.planetze.ecotracker.habits;

import static android.content.ContentValues.TAG;

import static com.b07.planetze.daily.food.MealDaily.MealType.FISH;
import static com.b07.planetze.daily.food.MealDaily.MealType.PLANT_BASED;
import static com.b07.planetze.daily.transport.DrivingDaily.VehicleType.ELECTRIC_CAR;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.b07.planetze.R;
import com.b07.planetze.daily.DailyType;
import com.b07.planetze.daily.energy.EnergyBillsDaily;
import com.b07.planetze.daily.food.MealDaily;
import com.b07.planetze.daily.transport.DrivingDaily;
import com.b07.planetze.daily.transport.FlightDaily;
import com.b07.planetze.database.data.DailyFetch;
import com.b07.planetze.database.data.DailyFetchList;
import com.b07.planetze.database.firebase.FirebaseDb;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import com.b07.planetze.ecotracker.EcoTrackerActivity;
import com.b07.planetze.ecotracker.EcoTrackerHomeFragment;
import com.b07.planetze.ecotracker.EcoTrackerViewModel;
import com.b07.planetze.util.DateInterval;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.result.Ok;


public class MyAdapterSelected extends RecyclerView.Adapter<MyAdapterSelected.MyViewHolder> {
    Context context;
    ArrayList<SuggestedHabit> habitList;
    FirebaseDb db = new FirebaseDb();

    public MyAdapterSelected(Context context, ArrayList<SuggestedHabit> habitList){
        this.context = context;
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.selected_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SuggestedHabit habit = habitList.get(position);
        String dailyType = habit.dailyType;
        holder.header.setText(habit.header);
        holder.category.setText(habit.category);
        holder.selectedDescription.setText(habit.selectedDescription);

        if(Objects.equals(dailyType, "FLIGHT") || Objects.equals(dailyType, "ENERGY_BILLS")){
            holder.trackSpinner.setVisibility(View.GONE);
            holder.week.setVisibility(View.GONE);
            holder.month.setVisibility(View.GONE);

            getDailyFreqInterval(DateInterval.between(LocalDate.now().minusYears(1),
                                LocalDate.now().plusDays(1)),
                                habit, db, holder);
        }
        else{
            holder.trackSpinner.setVisibility(View.VISIBLE);
            holder.trackSpinner.setSelection(holder.trackPos);
            holder.flight.setVisibility(View.GONE);
            holder.bills.setVisibility(View.GONE);
            if(Objects.equals(holder.track, "This Month")){
                holder.month.setVisibility(View.VISIBLE);
                holder.week.setVisibility(View.GONE);
                getDailyFreqInterval(DateInterval.month(YearMonth.now()),
                        habit, db, holder);
            }
            else if(Objects.equals(holder.track, "Past 4 Weeks")){
                holder.month.setVisibility(View.VISIBLE);
                holder.week.setVisibility(View.GONE);
                getDailyFreqInterval(DateInterval.between(LocalDate.now().minusMonths(1),
                                LocalDate.now().plusDays(1)),
                        habit, db, holder);
            }
            else if(Objects.equals(holder.track, "This Week")){
                holder.week.setVisibility(View.VISIBLE);
                holder.month.setVisibility(View.GONE);
                getDailyFreqInterval(DateInterval.week(LocalDate.now()),
                        habit, db, holder);
            }
            else if(Objects.equals(holder.track, "Past 7 Days")){
                holder.week.setVisibility(View.VISIBLE);
                holder.month.setVisibility(View.GONE);
                getDailyFreqInterval(DateInterval.between(LocalDate.now().minusWeeks(2),
                                LocalDate.now().plusDays(1)),
                        habit, db, holder);
            }
        }
        holder.loading.setVisibility(View.GONE);

        boolean isVisible = habit.visibility;
        holder.constraintLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        boolean isSelected = habit.selected;
        if(!isSelected){
            holder.constraintLayout.setVisibility(View.GONE);
            holder.constraintLayoutWhole.setVisibility(View.GONE);
        }


    }

    public void getDailyFreqInterval(DateInterval dateInterval, SuggestedHabit habit, FirebaseDb db, @NonNull MyViewHolder holder) {
        db.fetchDailies(dateInterval, result -> {
            if (result instanceof Ok<DailyFetchList, ?> r) {
                DailyFetchList list = r.get();
                getMatchingDailies(list, habit, holder);
            }
        });
    }
    public void getMatchingDailies(DailyFetchList list, SuggestedHabit habit, @NonNull MyViewHolder holder) {
        ArrayList<LocalDate> dailyDates = new ArrayList<LocalDate>();
        ArrayList<DailyFetch> bills = new ArrayList<DailyFetch>();
        String targetDailyType = habit.dailyType;
        for (DailyFetch fetch : list) {
            DailyType dailyType = fetch.daily().type();
            LocalDate dailyDate = fetch.date();

            if(Objects.equals(targetDailyType, "DRIVING")){
                if(fetch.daily() instanceof DrivingDaily){
                    DrivingDaily drivingDaily = (DrivingDaily)fetch.daily();
                    if(drivingDaily.vehicleType() == ELECTRIC_CAR){
                        if(!dailyDates.contains(dailyDate)){
                            dailyDates.add(dailyDate);
                        }

                    }
                }
            }
            else if(Objects.equals(targetDailyType, "FLIGHT")){
                if(fetch.daily() instanceof FlightDaily){
                    FlightDaily flightDaily = (FlightDaily)fetch.daily();
                    if(flightDaily.numberFlights() < 2){
                        if(!dailyDates.contains(dailyDate)){
                            dailyDates.add(dailyDate);
                        }
                    }
                }
            }
            else if(Objects.equals(targetDailyType, "MEAL")
                    && fetch.daily() instanceof MealDaily){
                MealDaily mealDaily = (MealDaily)fetch.daily();
                if(Objects.equals(habit.header, "Eat Less Meat")
                        && mealDaily.numberServings() <= 1){
                    if(!dailyDates.contains(dailyDate)){
                        dailyDates.add(dailyDate);
                    }
                }
                else if(Objects.equals(habit.header, "Adopt a Pescatarian Diet")
                        && mealDaily.mealType() == FISH){
                    if(!dailyDates.contains(dailyDate)){
                        dailyDates.add(dailyDate);
                    }
                }
                else if(Objects.equals(habit.header, "Adopt a Vegan/Vegetarian Diet")
                        && mealDaily.mealType() == PLANT_BASED){
                    if(!dailyDates.contains(dailyDate)){
                        dailyDates.add(dailyDate);
                    }
                }
            }else if(Objects.equals(targetDailyType, "ENERGY_BILLS")
                    && fetch.daily() instanceof EnergyBillsDaily){
                EnergyBillsDaily billDaily = (EnergyBillsDaily)fetch.daily();

                if(Objects.equals(habit.header, "Save Water")
                        && billDaily.billType().displayName().equals("water bills")){
                    if(!dailyDates.contains(dailyDate)){
                        bills.add(fetch);
                    }
                }
                else if(Objects.equals(habit.header, "Save Electricity")
                        && billDaily.billType().displayName().equals("electricity bills")){
                    if(!dailyDates.contains(dailyDate)){
                        bills.add(fetch);
                    }
                }
                else if(Objects.equals(habit.header, "Save Gas")
                        && billDaily.billType().displayName().equals("gas bills")){
                    if(!dailyDates.contains(dailyDate)){
                        bills.add(fetch);
                    }
                }
            }
            else if(((String) dailyType.toJson()).equals(targetDailyType)){
                if(!dailyDates.contains(dailyDate)){
                    dailyDates.add(dailyDate);
                }
            }
        }
        if(Objects.equals(targetDailyType, "FLIGHT")){
            calculateFrequenciesYear(dailyDates, holder, habit);
            holder.bills.setVisibility(View.GONE);
            holder.flight.setVisibility(View.VISIBLE);
        }
        else if(Objects.equals(targetDailyType, "ENERGY_BILLS")){
            calculateAmountYear(bills, holder, habit);
            holder.bills.setVisibility(View.VISIBLE);
            holder.flight.setVisibility(View.GONE);
        }
        else{
            if(Objects.equals(holder.track, "This Month")
                    || Objects.equals(holder.track, "Past 4 Weeks")){
                calculateFrequenciesMonth(dailyDates, holder, habit);
            }
            else if(Objects.equals(holder.track, "This Week")
                    || Objects.equals(holder.track, "Past 7 Days")){
                calculateFrequenciesWeek(dailyDates, holder, habit);
            }
        }
    }

    public void calculateFrequenciesMonth(ArrayList<LocalDate> dates, @NonNull MyViewHolder holder, SuggestedHabit habit){
        LocalDate startMonth = YearMonth.now().atDay(1);
        DateInterval week1 = DateInterval.between(startMonth, startMonth.plusWeeks(1));
        DateInterval week2 = DateInterval.between(startMonth.plusWeeks(1), startMonth.plusWeeks(2));
        DateInterval week3 = DateInterval.between(startMonth.plusWeeks(2), startMonth.plusWeeks(3));
        DateInterval week4 = DateInterval.between(startMonth.plusWeeks(3), YearMonth.now().plusMonths(1).atDay(1));

        if(Objects.equals(holder.track, "Past 4 Weeks")){
            week1 = DateInterval.between(LocalDate.now().minusWeeks(4), LocalDate.now().minusWeeks(3));
            week2 = DateInterval.between(LocalDate.now().minusWeeks(3), LocalDate.now().minusWeeks(2));
            week3 = DateInterval.between(LocalDate.now().minusWeeks(2), LocalDate.now().minusWeeks(1));
            week4 = DateInterval.between(LocalDate.now().minusWeeks(1), LocalDate.now().plusDays(1));

        }
        final int[] d1y = {0};
        final int[] d2y = {0};
        final int[] d3y = {0};
        final int[] d4y = {0};
        DateInterval finalWeek1 = week1;
        DateInterval finalWeek2 = week2;
        DateInterval finalWeek3 = week3;
        DateInterval finalWeek4 = week4;
        dates.forEach(date -> {
            if(finalWeek1.contains(date)){
                d1y[0]++;
            }
            if(finalWeek2.contains(date)){
                d2y[0]++;
            }
            if(finalWeek3.contains(date)){
                d3y[0]++;
            }
            if(finalWeek4.contains(date)){
                d4y[0]++;
            }
        });

        TextView w1 = holder.itemView.findViewById(R.id.monthw1);
        TextView w2 = holder.itemView.findViewById(R.id.monthw2);
        TextView w3 = holder.itemView.findViewById(R.id.monthw3);
        TextView w4 = holder.itemView.findViewById(R.id.monthw4);
        TextView w1A = holder.itemView.findViewById(R.id.monthw1Amount);
        TextView w2A = holder.itemView.findViewById(R.id.monthw2Amount);
        TextView w3A = holder.itemView.findViewById(R.id.monthw3Amount);
        TextView w4A =holder.itemView.findViewById(R.id.monthw4Amount);
        TextView label =holder.itemView.findViewById(R.id.labelMonth);
        TextView thisWeekLabel =holder.itemView.findViewById(R.id.monthw1Label);
        String dailyInfo = "";
        String w1AText = d1y[0]+"";
        if(d1y[0]==1) w1AText = d1y[0] + "";
        String w2AText = d2y[0] + "";
        if(d2y[0]==1) w2AText = d2y[0] + "";
        String w3AText = d3y[0] + "";
        if(d3y[0]==1) w3AText = d3y[0] + "";
        String w4AText = d4y[0] + "";
        if(d4y[0]==1) w4AText = d4y[0] + "";
        if(Objects.equals(holder.track, "This Month")){
            dailyInfo = "You've logged this activity " + (d1y[0]+d2y[0]+d3y[0]+d4y[0]) + " days this month. Here's a weekly breakdown of number of days logged per week:";
            if(d1y[0]+d2y[0]+d3y[0]+d4y[0]==1){
                dailyInfo = "You've logged this activity " + (d1y[0]+d2y[0]+d3y[0]+d4y[0]) + " day this month. Don't give up! Here's a weekly breakdown of number of days logged per week:";
            }
            thisWeekLabel.setVisibility(View.INVISIBLE);
            label.setText("Week of the Month");
            w1A.setText(w1AText);
            w2A.setText(w2AText);
            w3A.setText(w3AText);
            w4A.setText(w4AText);
        }
        if(Objects.equals(holder.track, "Past 4 Weeks")){
            dailyInfo = "You've logged this activity " + (d1y[0]+d2y[0]+d3y[0]+d4y[0]) + " days in the past 4 weeks. Here's a weekly breakdown of number of days logged per week:";
            if(d1y[0]+d2y[0]+d3y[0]+d4y[0]==1){
                dailyInfo = "You've logged this activity " + (d1y[0]+d2y[0]+d3y[0]+d4y[0]) + " day in the past 4 weeks. Don't give up! Here's a weekly breakdown of number of days logged per week:";
            }
            label.setText("Number of Weeks Ago");
            thisWeekLabel.setVisibility(View.VISIBLE);
            w1.setText("0");
            w2.setText("1");
            w3.setText("2");
            w4.setText("3");
            w1A.setText(w4AText);
            w2A.setText(w3AText);
            w3A.setText(w2AText);
            w4A.setText(w1AText);
        }
        holder.dailyType.setText(dailyInfo);
    }
    public void calculateFrequenciesWeek(ArrayList<LocalDate> dates, @NonNull MyViewHolder holder, SuggestedHabit habit){
        Log.d(TAG, "track mech in week: "+holder.track);
        LocalDate startWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        //this week
        DateInterval day1 = DateInterval.between(startWeek, startWeek.plusDays(1));
        DateInterval day2 = DateInterval.between(startWeek.plusDays(1), startWeek.plusDays(2));
        DateInterval day3 = DateInterval.between(startWeek.plusDays(2), startWeek.plusDays(3));
        DateInterval day4 = DateInterval.between(startWeek.plusDays(3), startWeek.plusDays(4));
        DateInterval day5 = DateInterval.between(startWeek.plusDays(4), startWeek.plusDays(5));
        DateInterval day6 = DateInterval.between(startWeek.plusDays(5), startWeek.plusDays(6));
        DateInterval day7 = DateInterval.between(startWeek.plusDays(6), startWeek.plusDays(7));

        if(Objects.equals(holder.track, "Past 7 Days")){
            day1 = DateInterval.between(LocalDate.now().minusDays(6), LocalDate.now().minusDays(5));
            day2 = DateInterval.between(LocalDate.now().minusDays(5), LocalDate.now().minusDays(4));
            day3 = DateInterval.between(LocalDate.now().minusDays(4), LocalDate.now().minusDays(3));
            day4 = DateInterval.between(LocalDate.now().minusDays(3), LocalDate.now().minusDays(2));
            day5 = DateInterval.between(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));
            day6 = DateInterval.between(LocalDate.now().minusDays(1), LocalDate.now());
            day7 = DateInterval.between(LocalDate.now(), LocalDate.now().plusDays(1));
        }

        final boolean[] d1y = {false};
        final boolean[] d2y = {false};
        final boolean[] d3y = {false};
        final boolean[] d4y = {false};
        final boolean[] d5y = {false};
        final boolean[] d6y = {false};
        final boolean[] d7y = {false};
        DateInterval finalDay1 = day1;
        DateInterval finalDay2 = day2;
        DateInterval finalDay3 = day3;
        DateInterval finalDay4 = day4;
        DateInterval finalDay5 = day5;
        DateInterval finalDay6 = day6;
        DateInterval finalDay7 = day7;
        dates.forEach(date -> {
            if(finalDay1.contains(date)){
                d1y[0] = true;
            }
            if(finalDay2.contains(date)){
                d2y[0] = true;
            }
            if(finalDay3.contains(date)){
                d3y[0] = true;
            }
            if(finalDay4.contains(date)){
                d4y[0] = true;
            }
            if(finalDay5.contains(date)){
                d5y[0] = true;
            }
            if(finalDay6.contains(date)){
                d6y[0] = true;
            }
            if(finalDay7.contains(date)){
                d7y[0] = true;
            }
        });

        TextView d1 = holder.itemView.findViewById(R.id.weekd1);
        TextView d2 = holder.itemView.findViewById(R.id.weekd2);
        TextView d3 = holder.itemView.findViewById(R.id.weekd3);
        TextView d4 = holder.itemView.findViewById(R.id.weekd4);
        TextView d5 = holder.itemView.findViewById(R.id.weekd5);
        TextView d6 = holder.itemView.findViewById(R.id.weekd6);
        TextView d7 = holder.itemView.findViewById(R.id.weekd7);
        ImageView d1A = holder.itemView.findViewById(R.id.day1Image);
        ImageView d2A = holder.itemView.findViewById(R.id.day2Image);
        ImageView d3A = holder.itemView.findViewById(R.id.day3Image);
        ImageView d4A = holder.itemView.findViewById(R.id.day4Image);
        ImageView d5A = holder.itemView.findViewById(R.id.day5Image);
        ImageView d6A = holder.itemView.findViewById(R.id.day6Image);
        ImageView d7A = holder.itemView.findViewById(R.id.day7Image);
        TextView label = holder.itemView.findViewById(R.id.label);
        TextView todayLabel = holder.itemView.findViewById(R.id.weekd1Label);
        String dailyInfo = "";
        int count = 0;
        if(d1y[0]){
            d1A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d1A.setImageResource(R.drawable.grey_x);
        if(d2y[0]){
            d2A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d2A.setImageResource(R.drawable.grey_x);
        if(d3y[0]){
            d3A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d3A.setImageResource(R.drawable.grey_x);
        if(d4y[0]){
            d4A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d4A.setImageResource(R.drawable.grey_x);
        if(d5y[0]){
            d5A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d5A.setImageResource(R.drawable.grey_x);
        if(d6y[0]){
            d6A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d6A.setImageResource(R.drawable.grey_x);
        if(d7y[0]){
            d7A.setImageResource(R.drawable.teal_check);
            count++;
        }
        else d7A.setImageResource(R.drawable.grey_x);

        if(Objects.equals(holder.track, "This Week")){
            dailyInfo = "You've logged this activity " + count + " days this week. Here's a daily breakdown:";
            if(count==1){
                dailyInfo = "You've logged this activity " + count + " day this week. Don't give up! Here's a daily breakdown:";
            }
            todayLabel.setVisibility(View.INVISIBLE);
            label.setText("Day of the Week");
            d1.setText("S");
            d2.setText("M");
            d3.setText("T");
            d4.setText("W");
            d5.setText("T");
            d6.setText("F");
            d7.setText("S");
        }
        if(Objects.equals(holder.track, "Past 7 Days")){
            count = 0;
            if(d7y[0]){
                d1A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d1A.setImageResource(R.drawable.grey_x);
            if(d6y[0]){
                d2A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d2A.setImageResource(R.drawable.grey_x);
            if(d5y[0]){
                d3A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d3A.setImageResource(R.drawable.grey_x);
            if(d4y[0]){
                d4A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d4A.setImageResource(R.drawable.grey_x);
            if(d3y[0]){
                d5A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d5A.setImageResource(R.drawable.grey_x);
            if(d2y[0]){
                d6A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d6A.setImageResource(R.drawable.grey_x);
            if(d1y[0]){
                d7A.setImageResource(R.drawable.teal_check);
                count++;
            }
            else d7A.setImageResource(R.drawable.grey_x);
            dailyInfo = "You've logged this activity " + count + " out of the past 7 days. Here's a daily breakdown:";
            if(count==1){
                dailyInfo = "You've logged this activity " + count + " out of the past 7 days. Don't give up! Here's a daily breakdown:";
            }
            todayLabel.setVisibility(View.VISIBLE);
            label.setText("Number of Days Ago");
            d1.setText("0");
            d2.setText("1");
            d3.setText("2");
            d4.setText("3");
            d5.setText("4");
            d6.setText("5");
            d7.setText("6");
        }
        holder.dailyType.setText(dailyInfo);
    }

    public void calculateFrequenciesYear(ArrayList<LocalDate> dates, @NonNull MyViewHolder holder, SuggestedHabit habit){
        LocalDate startyear = Year.from(LocalDate.now()).atMonth(Month.JANUARY).atDay(1);
        DateInterval q1 = DateInterval.between(startyear, startyear.plusMonths(3));
        DateInterval q2 = DateInterval.between(startyear.plusMonths(3), startyear.plusMonths(6));
        DateInterval q3 = DateInterval.between(startyear.plusMonths(6), startyear.plusMonths(9));
        DateInterval q4 = DateInterval.between(startyear.plusMonths(9), startyear.plusYears(1));
        final int[] d1y = {0};
        final int[] d2y = {0};
        final int[] d3y = {0};
        final int[] d4y = {0};
        dates.forEach(date -> {
            if(q1.contains(date)){
                d1y[0]++;
            }
            if(q2.contains(date)){
                d2y[0]++;
            }
            if(q3.contains(date)){
                d3y[0]++;
            }
            if(q4.contains(date)){
                d4y[0]++;
            }
        });

        TextView q1A = holder.itemView.findViewById(R.id.flightq1Amount);
        TextView q2A = holder.itemView.findViewById(R.id.flightq2Amount);
        TextView q3A = holder.itemView.findViewById(R.id.flightq3Amount);
        TextView q4A = holder.itemView.findViewById(R.id.flightq4Amount);

        String q1AText = d1y[0] + "";
        if(d1y[0]==1) q1AText = d1y[0] + "";
        String q2AText = d2y[0] + "";
        if(d2y[0]==1) q2AText = d2y[0] + "";
        String q3AText = d3y[0] + "";
        if(d3y[0]==1) q3AText = d3y[0] + "";
        String q4AText = d4y[0] + "";
        if(d4y[0]==1) q4AText = d4y[0] + "";

        int total = d1y[0]+d2y[0]+d3y[0]+d4y[0];
        String dailyInfo = "You've logged this activity " + total + " days this year.  Here's a breakdown of number of days logged per quarter of this year:";
        if(total==1){
            dailyInfo = "You've logged this activity " + total + " day this year. Here's a breakdown of number of days logged per quarter of this year:";
        }
        holder.dailyType.setText(dailyInfo);
        q1A.setText(q1AText);
        q2A.setText(q2AText);
        q3A.setText(q3AText);
        q4A.setText(q4AText);
    }

    public void calculateAmountYear(ArrayList<DailyFetch> list, @NonNull MyViewHolder holder, SuggestedHabit habit){
        DateInterval m1 = DateInterval.between(LocalDate.now().minusMonths(12), LocalDate.now().minusMonths(11));
        DateInterval m2 = DateInterval.between(LocalDate.now().minusMonths(11), LocalDate.now().minusMonths(10));
        DateInterval m3 = DateInterval.between(LocalDate.now().minusMonths(10), LocalDate.now().minusMonths(9));
        DateInterval m4 = DateInterval.between(LocalDate.now().minusMonths(9), LocalDate.now().minusMonths(8));
        DateInterval m5 = DateInterval.between(LocalDate.now().minusMonths(8), LocalDate.now().minusMonths(7));
        DateInterval m6 = DateInterval.between(LocalDate.now().minusMonths(7), LocalDate.now().minusMonths(6));
        DateInterval m7 = DateInterval.between(LocalDate.now().minusMonths(6), LocalDate.now().minusMonths(5));
        DateInterval m8 = DateInterval.between(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(4));
        DateInterval m9 = DateInterval.between(LocalDate.now().minusMonths(4), LocalDate.now().minusMonths(3));
        DateInterval m10 = DateInterval.between(LocalDate.now().minusMonths(3), LocalDate.now().minusMonths(2));
        DateInterval m11 = DateInterval.between(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1));
        DateInterval m12 = DateInterval.between(LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1));
        final double[] d1y = {0};
        final double[] d2y = {0};
        final double[] d3y = {0};
        final double[] d4y = {0};
        final double[] d5y = {0};
        final double[] d6y = {0};
        final double[] d7y = {0};
        final double[] d8y = {0};
        final double[] d9y = {0};
        final double[] d10y = {0};
        final double[] d11y = {0};
        final double[] d12y = {0};
        for (DailyFetch dailyFetch : list) {
            LocalDate date = dailyFetch.date();
            double price = 4;
            if(dailyFetch.daily() instanceof EnergyBillsDaily){
                EnergyBillsDaily daily = (EnergyBillsDaily) dailyFetch.daily();
                price = daily.billAmount();
            }
            Log.d(TAG, "amtyr price: " + price);
            if(m1.contains(date)){
                d1y[0] += price;
            }
            if(m2.contains(date)){
                d2y[0] += price;
            }
            if(m3.contains(date)){
                d3y[0] += price;
            }
            if(m4.contains(date)){
                d4y[0] += price;
            }
            if(m5.contains(date)){
                d5y[0] += price;
            }
            if(m6.contains(date)){
                d6y[0] += price;
            }
            if(m7.contains(date)){
                d7y[0] += price;
            }
            if(m8.contains(date)){
                d8y[0] += price;
            }
            if(m9.contains(date)){
                d9y[0] += price;
            }
            if(m10.contains(date)){
                d10y[0] += price;
            }
            if(m11.contains(date)){
                d11y[0] += price;
            }
            if(m12.contains(date)){
                d12y[0] += price;
            }
        }
        double total = d1y[0]+d2y[0]+d3y[0]+d4y[0]+d5y[0]+d6y[0]+d7y[0]+d8y[0]+d9y[0]+d10y[0]+d11y[0]+d12y[0];
        double avg = total / list.size();
        TextView currentCost = holder.itemView.findViewById(R.id.currentAmount);
        TextView avgCost = holder.itemView.findViewById(R.id.avgAmount);
        String currentText = "$"+total;
        String avgText = "$"+avg;
        String dailyInfo = "You've logged this activity " + list.size() + " days in the past year. Here's a cost breakdown:";
        if(list.size()==1){
            dailyInfo = "You've logged this activity " + list.size() + " day in the past year. Here's a cost breakdown:";
        }
        holder.dailyType.setText(dailyInfo);
        currentCost.setText (currentText);
        avgCost.setText (avgText);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener{
        TextView header;
        TextView dailyType;
        TextView category;
        TextView selectedDescription;
        Button unselect;
        Button ecotracker;
        Spinner trackSpinner;
        int trackPos;
        String track = "";
        ConstraintLayout constraintLayout;
        ConstraintLayout constraintLayoutWhole;
        ConstraintLayout loading;
        ConstraintLayout week;
        ConstraintLayout month;
        ConstraintLayout flight;
        ConstraintLayout bills;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            header = itemView.findViewById(R.id.header);
            category = itemView.findViewById(R.id.category);
            selectedDescription = itemView.findViewById(R.id.description);
            dailyType = itemView.findViewById(R.id.daily);

            constraintLayout = itemView.findViewById(R.id.infoContainer);
            constraintLayoutWhole = itemView.findViewById(R.id.habit);
            loading = itemView.findViewById(R.id.load);
            week = itemView.findViewById(R.id.week);
            month = itemView.findViewById(R.id.month);
            flight = itemView.findViewById(R.id.flight);
            bills = itemView.findViewById(R.id.bills);

            unselect = itemView.findViewById(R.id.unselect);
            ecotracker = itemView.findViewById(R.id.ecotracker);

            Spinner spinner = itemView.findViewById(R.id.track);
            trackSpinner = spinner;

            // Create an ArrayAdapter using the string array and a default spinner layout.
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    itemView.getContext(),
                    R.array.habit_track,
                    android.R.layout.simple_spinner_item
            );
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner.
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

                    //onclick to expand
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition()>-1) {
                        SuggestedHabit habit = habitList.get(getAdapterPosition());
                        habit.setVisibility(!habit.isVisibility());
                        notifyItemChanged(getAdapterPosition());
                    }
                }
            });

            ecotracker.setOnClickListener(v -> EcoTrackerActivity.start(context));

            unselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuggestedHabit habit = habitList.get(getAdapterPosition());

                    HabitProcessor habitProcessor = new HabitProcessor(itemView.getContext(), "habits.json");

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
                    SuggestedHabit habitToDelete = habitList.get(getAdapterPosition());
                    habitToDelete.setSelected(!habitToDelete.isSelected());
                    habitList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), habitList.size());
                }
            });
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String filter = parent.getItemAtPosition(position).toString();
            SuggestedHabit habit = habitList.get(getAdapterPosition());

            if(!Objects.equals(habit.dailyType, "FLIGHT") && !Objects.equals(habit.dailyType, "ENERGY_BILLS")){
                if(filter.equals("This Month")){
                    track = "This Month";
                }
                else if(filter.equals("Past 4 Weeks")){
                    track = "Past 4 Weeks";
                }
                else if(filter.equals("This Week")){
                    track = "This Week";
                }
                else if(filter.equals("Past 7 Days")){
                    track = "Past 7 Days";
                }
                trackPos = position;
                loading.setVisibility(View.VISIBLE);
                notifyItemChanged(getAdapterPosition());
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
