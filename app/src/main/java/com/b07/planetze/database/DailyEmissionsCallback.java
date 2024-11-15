package com.b07.planetze.database;

import com.b07.planetze.util.Mass;
import com.b07.planetze.util.Result;

public interface DailyEmissionsCallback {
    /**
     * A callback called by <code>Database.fetchDailyEmissions()</code>.
     * @param result the mass of daily CO2e emissions or an error
     */
    void dailyEmissionsCallback(Result<Mass, DatabaseError> result);
}
