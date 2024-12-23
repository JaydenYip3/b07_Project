package com.b07.planetze.common;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.b07.planetze.database.ToJson;
import com.b07.planetze.util.measurement.Mass;
import com.b07.planetze.util.Util;
import com.b07.planetze.util.immutability.ImmutableList;
import com.b07.planetze.util.immutability.MutableWithCopy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores CO2e emissions by category.
 */
public final class Emissions
        implements MutableWithCopy<Emissions>, ToJson, Parcelable {
    @NonNull private final ImmutableList<Mass> categories;

    private Emissions() {
        List<Mass> list = Util.filledArrayList(4, Mass::zero);
        categories = new ImmutableList<>(list);
    }

    /**
     * {@return a new <code>Emissions</code> where all categories are zero}
     */
    @NonNull
    public static Emissions zero() {
        return new Emissions();
    }

    /**
     * {@return a new <code>Emissions</code> with specified transportation
     *          emissions (and zero for everything else)}
     * @param mass the mass of transportation emissions
     */
    @NonNull
    public static Emissions transport(Mass mass) {
        Emissions e = zero();
        e.transport().set(mass);
        return e;
    }

    /**
     * {@return a new <code>Emissions</code> with specified energy emissions
     *          (and zero for everything else)}
     * @param mass the mass of energy emissions
     */
    @NonNull
    public static Emissions energy(Mass mass) {
        Emissions e = zero();
        e.energy().set(mass);
        return e;
    }

    /**
     * {@return a new <code>Emissions</code> with specified food emissions
     *          (and zero for everything else)}
     * @param mass the mass of food emissions
     */
    @NonNull
    public static Emissions food(Mass mass) {
        Emissions e = zero();
        e.food().set(mass);
        return e;
    }

    /**
     * {@return a new <code>Emissions</code> with specified shopping emissions
     *          (and zero for everything else)}
     * @param mass the mass of shopping emissions
     */
    @NonNull
    public static Emissions shopping(Mass mass) {
        Emissions e = zero();
        e.shopping().set(mass);
        return e;
    }

    /**
     * {@return a mutable reference to transportation emissions}
     */
    @NonNull
    public Mass transport() {
        return categories.get(0);
    }

    /**
     * {@return a mutable reference to energy use emissions}
     */
    @NonNull
    public Mass energy() {
        return categories.get(1);
    }

    /**
     * {@return a mutable reference to food consumption emissions}
     */
    @NonNull
    public Mass food() {
        return categories.get(2);
    }

    /**
     * {@return a mutable reference to shopping emissions}
     */
    @NonNull
    public Mass shopping() {
        return categories.get(3);
    }

    /**
     * {@return a new <code>Mass</code> - the sum of emissions across all
     *          categories}
     */
    @NonNull
    public Mass total() {
        Mass sum = Mass.zero();
        categories.forEach(sum::add);
        return sum;
    }

    /**
     * Sets the values of <code>this</code> to the values of
     * another {@link Emissions}.
     * @param other the {@link Emissions} to set <code>this</code> to
     * @return <code>this</code>
     */
    @NonNull
    public Emissions set(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::set);
        return this;
    }

    /**
     * Adds (category-wise) another {@link Emissions}
     * to <code>this</code>.
     * @param other the {@link Emissions} to add
     * @return <code>this</code>
     */
    @NonNull
    public Emissions add(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::add);
        return this;
    }

    /**
     * Subtracts (category-wise) another {@link Emissions}
     * from <code>this</code>.
     * @param other the {@link Emissions} to subtract
     * @return <code>this</code>
     */
    @NonNull
    public Emissions subtract(@NonNull Emissions other) {
        Util.zip(categories, other.categories).forEach(Mass::subtract);
        return this;
    }

    /**
     * Multiplies the emissions of all categories by a scalar.
     * @param scalar the scaling factor
     * @return <code>this</code>
     */
    @NonNull
    public Emissions scale(double scalar) {
        categories.forEach(c -> c.scale(scalar));
        return this;
    }

    /**
     * Flips the sign of the emissions of all categories.
     * @return <code>this</code>
     */
    @NonNull
    public Emissions negate() {
        categories.forEach(Mass::negate);
        return this;
    }

    @NonNull
    @Override
    public Emissions copy() {
        Emissions emissions = new Emissions();
        Util.zip(emissions.categories, categories).forEach(Mass::set);
        return emissions;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Emissions[transportation=%s, energy=%s, food=%s, shopping=%s]",
                transport(),
                energy(),
                food(),
                shopping());
    }

    @NonNull
    @Override
    public Emissions self() {
        return this;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static Emissions fromJson(@NonNull Object o) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) o;

        Emissions e = Emissions.zero();
        e.transport().set(Mass.fromJson(map.get("transport")));
        e.energy().set(Mass.fromJson(map.get("energy")));
        e.food().set(Mass.fromJson(map.get("food")));
        e.shopping().set(Mass.fromJson(map.get("shopping")));
        return e;
    }

    @NonNull
    @Override
    public Object toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("transport", transport().toJson());
        map.put("energy", energy().toJson());
        map.put("food", food().toJson());
        map.put("shopping", shopping().toJson());
        return map;
    }

    public static final Parcelable.Creator<Emissions> CREATOR
            = new Parcelable.Creator<>() {
        public Emissions createFromParcel(Parcel in) {
            Emissions e = new Emissions();
            e.categories.forEach(c -> c.set(Mass.CREATOR.createFromParcel(in)));
            return e;
        }

        public Emissions[] newArray(int size) {
            return new Emissions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        categories.forEach(c -> dest.writeParcelable(c, 0));
    }
}
