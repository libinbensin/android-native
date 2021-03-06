package com.android.foodbook.utils;

import android.location.Location;

import com.android.foodbook.MainApplication;
import com.android.foodbook.constants.AppConstant;

import java.text.DecimalFormat;

public class AppLocationUtil
{
    /**
     * method to calculate distance from current selected location
     *
     */
    public static String getDistance(String lat, String lng)
    {
        if(!AppUtil.hasValue(lat) || !AppUtil.hasValue(lng))
        {
            return AppConstant.BLANK;
        }
        Double toLat = Double.valueOf(lat);
        Double toLng = Double.valueOf(lng);

        Location from = MainApplication.getAppInstance().getLastLocation();
        float[] results = {0 };

        Location.distanceBetween(from.getLatitude(), from.getLongitude(), toLat, toLng, results);

        Double f = results[0] * 0.00062137119;

        return new DecimalFormat("#.##").format(f);
    }
}
