package com.android.foodmark;

import com.android.foodmark.database.SQLiteAsyncTask;
import com.android.foodmark.utils.ImageDownloader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;

public class MainApplication extends Application
{
	
	// Google API key
	private final static String GOOGLE_API_KEY = "AIzaSyDQ5LSi13ighFRcp_JU7kXiKXnrjbPDiuk";
	
	private static MainApplication appInstance;
	
	private static ImageDownloader imageDownloader = new ImageDownloader();
	
	private static SharedPreferences preferences;
	
	private  Location mLocation = null;

    private SQLiteDatabase mSQLiteDatabase = null;
	
	public static MainApplication getAppInstance()
	{	
		return appInstance;
	}

	public static SharedPreferences getPreferences()
	{
		return preferences;
	}
	public String getGoogleApiKey()
	{
		return GOOGLE_API_KEY;
	}
	
	public static ImageDownloader getImageDownloader()
	{
		return imageDownloader;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		appInstance = this;
		initPreference();
	}

    private void initSQLite()
    {
        // create an sqlite task instance to access the database
        SQLiteAsyncTask sqLiteAsyncTask = new SQLiteAsyncTask(getApplicationContext());
        mSQLiteDatabase = (SQLiteDatabase) sqLiteAsyncTask.loadInBackground();
    }

    public SQLiteDatabase getSQLiteInstance()
    {
        return mSQLiteDatabase;
    }

    public void setLocation(Location location)
	{
		mLocation = location;
	}
	
	public Location getLocation()
	{
		if(mLocation == null)
		{
			LocationManager  mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return mLocation;
	}
	
	public void resetLocation()
	{
		if(mLocation != null)
		{
			mLocation.reset();
		}
		mLocation = null;
	}

	private void initPreference() 
	{
		preferences = getSharedPreferences("private preference", MODE_PRIVATE);
	}
	
}
