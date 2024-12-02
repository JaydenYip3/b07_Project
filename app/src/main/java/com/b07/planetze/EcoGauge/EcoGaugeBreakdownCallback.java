package com.b07.planetze.EcoGauge;

import java.util.Map;

public interface EcoGaugeBreakdownCallback {
    Map<String, Double> getEmissionsByCategory();
}
