package com.b07.planetze.EcoGauge;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public interface EcoGaugeTrendsCallback {
    public LineGraphSeries<DataPoint> getDataPoints();
}