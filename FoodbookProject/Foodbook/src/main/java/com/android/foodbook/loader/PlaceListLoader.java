package com.android.foodbook.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodbook.MainApplication;
import com.android.foodbook.constants.AppConfig;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.dataaccess.HttpHelper;
import com.android.foodbook.json.PlaceListResponse;
import com.android.foodbook.utils.AppUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

public class PlaceListLoader extends BaseLoader<PlaceListResponse>
{
	private String mType;
	public PlaceListLoader(final Context argContext, Bundle argBundle)
	{
		super(argContext);
		mType = argBundle.getString(AppConstant.TYPE);
	}
 
	@Override
	protected String getRequest() 
	{
		//LocationManager  mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Location location = MainApplication.getAppInstance().getSearchLocation();//mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        String radius = AppUtil.getRadius();

		final String requestUrl = String.format(
                AppConfig.GOOGLE_PLACE_RESTAURANT_URL,mType,
                "true",
                MainApplication.getAppInstance().getGoogleApiKey(),
                Double.toString(location.getLatitude()),
                Double.toString(location.getLongitude()),
                radius);
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<PlaceListResponse, Exception> runOnBackground()
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			PlaceListResponse placeListResponse = new Gson().fromJson(new InputStreamReader(inputStream),PlaceListResponse.class);
			
			return new LoaderResultOrException<PlaceListResponse, Exception>(placeListResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<PlaceListResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<PlaceListResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<PlaceListResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<PlaceListResponse, Exception>(e);
		}
	}

}
