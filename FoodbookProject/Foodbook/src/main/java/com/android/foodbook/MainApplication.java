package com.android.foodbook;

import com.android.foodbook.constants.AppPrefs;
import com.android.foodbook.database.SQLiteAsyncTask;
import com.android.foodbook.security.AppSecurity;
import com.android.foodbook.utils.ImageDownloader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainApplication extends Application
{
	
	// Google API key
	private final static String GOOGLE_API_KEY = "AIzaSyDQ5LSi13ighFRcp_JU7kXiKXnrjbPDiuk";

    private static String mThemeName = "Light"; // initialize default theme

    private static MainApplication appInstance;
	
	private static ImageDownloader imageDownloader = new ImageDownloader();
	
	private static SharedPreferences preferences;
	
	private  Location mSearchLocation = null;

    private Location mLastLocation = null;

    private SQLiteDatabase mSQLiteDatabase = null;

    private static byte[] mSecretKey;
	
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

    public static byte[] getSecretKey() { return mSecretKey; }

    public static String getThemeName() {return mThemeName;}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		appInstance = this;
		initPreference();
        initSQLite();
        initSecurity();
	}

    public static void intTheme()
    {
        mThemeName = getPreferences().getString(AppPrefs.APP_THEME,AppPrefs.DEFAULT_THEME);
    }

    private void initSecurity()
    {
        final String secretFile = "secure_file";
        boolean keyExists = false;
        //check if secret key exist in secure file
        try
        {
            FileInputStream inputStream = openFileInput(secretFile);
            mSecretKey = new byte[16];
            int result = inputStream.read(mSecretKey);
            if(result >0)
            {
                keyExists = true;
            }
            inputStream.close();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
        if(!keyExists)
        {
            // generate a key
            mSecretKey = AppSecurity.getSecretKey();
            if(mSecretKey != null)
            {
                // write in a secure file inside the app
                try
                {
                    // MODE_PRIVATE will create the file (or replace a file of the same name)
                    // and make it private to the application.
                    FileOutputStream outputStream = openFileOutput(secretFile,Context.MODE_PRIVATE);
                    outputStream.write(mSecretKey);
                    outputStream.close();
                }
                catch (FileNotFoundException e)
                {

                }
                catch (IOException e)
                {

                }
            }
        }
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

    public void setSearchLocation(Location location)
	{
        mSearchLocation = location;
	}
	
	public Location getSearchLocation()
	{
		/*if(mSearchLocation == null)
		{
			LocationManager  mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}*/
		return mSearchLocation;
	}

    public void setLastLocation(Location location)
    {
        mLastLocation = location;
    }

    public Location getLastLocation()
    {
		/*if(mSearchLocation == null)
		{
			LocationManager  mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}*/
        return mLastLocation;
    }
	
	public void resetLocation()
	{
		if(mSearchLocation != null)
		{
            mSearchLocation.reset();
		}
        mSearchLocation = null;
	}

	private void initPreference() 
	{
		preferences = getSharedPreferences("private preference", MODE_PRIVATE);
	}


	
}
