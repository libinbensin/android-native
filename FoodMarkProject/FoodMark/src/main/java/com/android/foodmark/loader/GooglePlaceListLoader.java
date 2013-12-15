package com.android.foodmark.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.AppConstants;
import com.android.foodmark.constants.ConfigConstants;
import com.android.foodmark.dataaccess.HttpHelper;
import com.android.foodmark.json.GooglePlaceResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

public class GooglePlaceListLoader extends BaseLoader<GooglePlaceResponse>
{
	private String mType;
	public GooglePlaceListLoader(final Context argContext, Bundle argBundle) 
	{
		super(argContext);
		mType = argBundle.getString(AppConstants.TYPE);
	}
 
	@Override
	protected String getRequest() 
	{
		//LocationManager  mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Location location = MainApplication.getAppInstance().getLocation();//mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
		final String requestUrl = String.format(ConfigConstants.GOOGLE_PLACE_RESTAURANT_URL,mType, "true",MainApplication.getAppInstance().getGoogleApiKey(),Double.toString(location.getLatitude()),Double.toString(location.getLongitude()));
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<GooglePlaceResponse, Exception> runOnBackground() 
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			GooglePlaceResponse googlePlaceResponse = new Gson().fromJson(new InputStreamReader(inputStream),GooglePlaceResponse.class);
			
			return new LoaderResultOrException<GooglePlaceResponse, Exception>(googlePlaceResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<GooglePlaceResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<GooglePlaceResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<GooglePlaceResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<GooglePlaceResponse, Exception>(e);
		}
	}

}
