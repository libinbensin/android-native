package com.android.foodmark.utils;

import android.location.Location;

import com.android.foodmark.MainApplication;
import com.android.foodmark.model.Geometry;

import java.text.DecimalFormat;

public class AppLocationUtil
{
    /**
     * method to calculate distance from current selected location
     *
     */
    public static String getDistance(String lat, String lng)
    {
        Double toLat = Double.valueOf(lat);
        Double toLng = Double.valueOf(lng);

        Location from = MainApplication.getAppInstance().getLastLocation();
        float[] results = {0 };

        Location.distanceBetween(from.getLatitude(), from.getLongitude(), toLat, toLng, results);

        Double f = results[0] * 0.00062137119;

        return new DecimalFormat("#.##").format(f);
    }
}
