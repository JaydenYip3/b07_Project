package com.b07.planetze.onboarding;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.b07.planetze.R;
import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.User;
import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.util.measurement.Mass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CalcDisplayFragment extends Fragment {
    Emissions userEmissions = Emissions.zero();
    User currentUser;
    double[] emissions = new double[4];
    FirebaseDb db = new FirebaseDb();

    CalcDisplayFragment(double[] allEmissions){
        emissions[0] = allEmissions[0];
        emissions[1] = allEmissions[1];
        emissions[2] = allEmissions[2];
        emissions[3] = allEmissions[3];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc_display, container, false);

        TextView total = view.findViewById(R.id.total);
        TextView transport = view.findViewById(R.id.transportAmount);
        TextView food = view.findViewById(R.id.foodAmount);
        TextView housing = view.findViewById(R.id.housingAmount);
        TextView consumption = view.findViewById(R.id.consumptionAmount);
        TextView nationalAvg = view.findViewById(R.id.textView8);
        TextView globalTarget = view.findViewById(R.id.textView9);

        Mass t = userEmissions.transport();
        t.set(Mass.zero());
        t.set(Mass.kg(emissions[0]));
        Mass f = userEmissions.food();
        f.set(Mass.zero());
        f.set(Mass.kg(emissions[1]));
        Mass h = userEmissions.energy();
        h.set(Mass.zero());
        h.set(Mass.kg(emissions[2]));
        Mass c = userEmissions.shopping();
        c.set(Mass.zero());
        c.set(Mass.kg(emissions[3]));

        db.postOnboardingEmissions(userEmissions,result2 -> {
            result2.match(user2 -> Log.d(TAG, "user found")
                    , dbError -> { // if this operation failed:
                        Log.d(TAG, "error: " + dbError);
                    });
        });

        Mass totalMass = userEmissions.total();
        Log.d(TAG, "emissions: " + userEmissions);
        Log.d(TAG, "mass total: " + totalMass);

        double totalAmount= Math.round(((double)totalMass.toJson())*(1/0.45359237)/200)/10.0;
        String amountText = String.valueOf(totalAmount);
        String globalTargetMet = "Your footprint has reached global targets for climate change reduction of 2 tons CO2e/year! Good job!";
        total.setText(amountText);
        if(totalAmount<2){
            globalTarget.setText(globalTargetMet);
        }

        Mass transportMass = userEmissions.transport();
        double transportAmount = Math.round(((double)transportMass.toJson())*(1/0.45359237)/200)/10.0;
        String transportAmountText = transportAmount + " tons of CO2e/year";
        Mass foodMass = userEmissions.food();
        double foodAmount = Math.round(((double)foodMass.toJson())*(1/0.45359237)/200)/10.0;
        String foodAmountText = foodAmount + " tons of CO2e/year";
        Mass housingMass = userEmissions.energy();
        double housingAmount = Math.round(((double)housingMass.toJson())*(1/0.45359237)/200)/10.0;
        String housingAmountText = housingAmount + " tons of CO2e/year";
        Mass consumptionMass = userEmissions.shopping();
        double consumptionAmount = Math.round(((double)consumptionMass.toJson())*(1/0.45359237)/200)/10.0;
        String consumptionAmountText = consumptionAmount + " tons of CO2e/year";

        transport.setText(transportAmountText);
        food.setText(foodAmountText);
        housing.setText(housingAmountText);
        consumption.setText(consumptionAmountText);

        db.fetchUser(result2 -> {
            result2.match(userOption2 -> { // if this operation was successful:
                userOption2.match(// if the user has user info set:
                        user2 -> {
                            currentUser = user2;
                            Log.d(TAG, "User found");
                            Map<String, String> userJson = (HashMap<String, String>) currentUser.toJson();
                            String country = userJson.get("country");
                            CountryProcessor countryProcessor = new CountryProcessor(view.getContext(), "countries.json");
                            double regionAverage = countryProcessor.getResult(country);
                            double percentage;
                            String descriptor = "";
                            if (totalAmount > regionAverage){
                                percentage = Math.round(1000 * totalAmount / regionAverage)/10.0;
                                descriptor = "above";
                            }
                            else{
                                percentage = Math.round(1000 * (1 - totalAmount / regionAverage))/10.0;
                                descriptor = "below";
                            }
                            String avgText = "Your annual carbon footprint is " + percentage + "% " + descriptor + " the national average for " + country + ": "  + regionAverage;
                            if (totalAmount == regionAverage){
                                avgText = "Your annual carbon footprint is the same as national average for " + country + ": " + regionAverage;
                            }
                            nationalAvg.setText(avgText);
                        },
                        () -> Log.d(TAG, "user nonexistent"));
            }, dbError -> { // if this operation failed:
                Log.d(TAG, "error: " + dbError);
            });
        });


        Button buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QuestionsConsumptionFragment(emissions));
            }
        });

        Button buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFragment(new QuestionsHousingFragment());   //change to ecotracker!!-----------------------
            }
        });

        return view;
    }

    public void onSubmit(@NonNull View v) {
        //loadFragment(new QuestionsFoodFragment());

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
