package com.android.foodmark.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import android.content.Context;
import android.location.LocationManager;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.constants.AppPrefs;

public final class AppUtil 
{
	
	private AppUtil() 
	{
	}
	
	public static String toLowerCase(String s)
	{
		if(s == null)
		{
			return AppConstant.BLANK;
		}
		return s.toLowerCase(Locale.US);
	}
	
	public static boolean hasValue(String s)
	{
		return ((s != null && s.length() > 0) ? true : false);
	}
	
	public static void setImageAnimation(ImageView imageView)
	{
		setImageAnimation(imageView,2000);
	}
	
	public static void setImageAnimation(ImageView imageView, int duration)
	{
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, .5f,
                ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(duration);
        imageView.startAnimation(scale);  
	}
	
	public static int getLength(String s)
	{
		return (s != null ? s.length() : 0);
	}
	
	public static void showToast(String s)
	{
		Toast.makeText(MainApplication.getAppInstance().getApplicationContext(), s, Toast.LENGTH_LONG).show();
	}
	
	public static String toString(byte[] bytes)
    {
        try {
            String s = new String(bytes ,"UTF-8");
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] toBytes(String s)
    {
        try
        {
            return s.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static double toDouble(String s)
    {
        if(hasValue(s))
        {
            return Double.valueOf(s);
        }
        else
        {
            return 0;
        }
    }

    public static boolean isGPSEnabled()
    {
        LocationManager mLocationManager = (LocationManager) MainApplication.getAppInstance()
                .getSystemService(Context.LOCATION_SERVICE);
        if(mLocationManager != null && mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            return true;
        }
        return false;
    }

    public static String getRadius()
    {
        return MainApplication.getPreferences()
                .getString(AppPrefs.SEARCH_RADIUS,AppPrefs.DEFAULT_RADIUS);
    }

    public static void setRadius(String s)
    {
        MainApplication.getPreferences()
                .edit().putString(AppPrefs.SEARCH_RADIUS,s).commit();
    }

    public static String getLaunchType()
    {
        return MainApplication.getPreferences()
                .getString(AppPrefs.TYPE,AppPrefs.DEFAULT_TYPE);
    }

    public static void setLaunchType()
    {
        return;
    }

}
