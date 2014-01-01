package com.android.foodmark.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.AppConstant;

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
}
