package com.b07.planetze.ecotracker.daily;

import androidx.annotation.NonNull;

import com.b07.planetze.common.Emissions;
import com.b07.planetze.common.measurement.Distance;
import com.b07.planetze.common.measurement.ImmutableDistance;
import com.b07.planetze.common.measurement.Mass;
import com.b07.planetze.util.immutability.ImmutableCopy;

public record DrivingDaily(
        @NonNull Vehicle vehicle,
        @NonNull ImmutableDistance distance
) implements Daily {
    public enum Vehicle {
        CAR
    }

    @NonNull
    @Override
    public Emissions emissions() {
        return switch(vehicle) {
            case CAR -> Emissions.transport(Mass.g(192).scale(distance.km()));
        };
    }

    @NonNull
    @Override
    public String type() {
        return "driving";
    }
}
