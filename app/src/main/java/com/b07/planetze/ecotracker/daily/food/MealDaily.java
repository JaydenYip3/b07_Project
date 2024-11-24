package com.b07.planetze.ecotracker.daily.food;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.ecotracker.daily.Daily;
import com.b07.planetze.ecotracker.daily.DailyType;

public record MealDaily(
        @NonNull MealType mealType,
        int numberServings
) implements Daily {
    private static final double BEEF_KG_CO2E_PER_SERVING = 15.5;
    private static final double PORK_KG_CO2E_PER_SERVING = 2.4;
    private static final double CHICKEN_KG_CO2E_PER_SERVING = 1.82;
    private static final double FISH_KG_CO2E_PER_SERVING = 1.34;
    private static final double PLANT_BASED_KG_CO2E_PER_SERVING = 0.2;

    @NonNull
    @Override
    public Emissions emissions() {
        return Emissions.food(Mass.kg(switch(mealType) {
            case BEEF ->
                    BEEF_KG_CO2E_PER_SERVING * numberServings;
            case PORK ->
                    PORK_KG_CO2E_PER_SERVING * numberServings;
            case CHICKEN ->
                    CHICKEN_KG_CO2E_PER_SERVING * numberServings;
            case FISH ->
                    FISH_KG_CO2E_PER_SERVING * numberServings;
            case PLANT_BASED ->
                    PLANT_BASED_KG_CO2E_PER_SERVING * numberServings;
        }));
    }

    @NonNull
    @Override
    public DailyType type() {
        return DailyType.MEAL;
    }

    public enum MealType {
        BEEF,
        PORK,
        CHICKEN,
        FISH,
        PLANT_BASED
    }
}
