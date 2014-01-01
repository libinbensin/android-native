package com.android.foodmark.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.AppConfig;
import com.android.foodmark.dataaccess.HttpHelper;
import com.android.foodmark.json.PlaceDetailResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;

public class PlaceDetailLoader extends BaseLoader<PlaceDetailResponse>
{
	
	private String mQuery;
	
	public PlaceDetailLoader(final Context argContext, final String argQuery)
	{
		super(argContext);
		this.mQuery = argQuery;
	}
 
	@Override
	protected String getRequest() 
	{
		final String requestUrl = String.format(AppConfig.GOOGLE_PLACE_DETAIL_URL, mQuery,"true",MainApplication.getAppInstance().getGoogleApiKey());
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<PlaceDetailResponse, Exception> runOnBackground()
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			PlaceDetailResponse placeDetailResponse = new Gson().fromJson(new InputStreamReader(inputStream),PlaceDetailResponse.class);
			
			return new LoaderResultOrException<PlaceDetailResponse, Exception>(placeDetailResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<PlaceDetailResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<PlaceDetailResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<PlaceDetailResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<PlaceDetailResponse, Exception>(e);
		}
	}

}
