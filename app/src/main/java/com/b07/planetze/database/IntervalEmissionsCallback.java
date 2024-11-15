package com.b07.planetze.database;

import com.b07.planetze.util.Result;

import java.util.List;

public interface IntervalEmissionsCallback {
    /**
     * A callback called by <code>Database.fetchEmissionsOverInterval()</code>.
     * @param result a list of the mass and date CO2e emissions
     *               over the specified interval or an error
     */
    void intervalEmissionsCallback(Result<List<DatedEmissions>, DatabaseError> result);
}
