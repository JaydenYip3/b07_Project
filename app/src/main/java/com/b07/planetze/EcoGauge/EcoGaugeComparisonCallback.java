package com.b07.planetze.EcoGauge;

import com.b07.planetze.database.firebase.FirebaseDb;
import com.b07.planetze.onboarding.CountryProcessor;

public interface EcoGaugeComparisonCallback {
    String getComparisonText(CountryProcessor countryProcessor, FirebaseDb database);
}
