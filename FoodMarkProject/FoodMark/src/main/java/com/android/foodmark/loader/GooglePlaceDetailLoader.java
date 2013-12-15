package com.android.foodmark.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.ConfigConstants;
import com.android.foodmark.dataaccess.HttpHelper;
import com.android.foodmark.json.GooglePlaceDetailResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;

public class GooglePlaceDetailLoader extends BaseLoader<GooglePlaceDetailResponse>
{
	
	private String mQuery;
	
	public GooglePlaceDetailLoader(final Context argContext,final String argQuery) 
	{
		super(argContext);
		this.mQuery = argQuery;
	}
 
	@Override
	protected String getRequest() 
	{
		final String requestUrl = String.format(ConfigConstants.GOOGLE_PLACE_DETAIL_URL, mQuery,"true",MainApplication.getAppInstance().getGoogleApiKey());
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<GooglePlaceDetailResponse, Exception> runOnBackground() 
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			GooglePlaceDetailResponse googlePlaceDetailResponse = new Gson().fromJson(new InputStreamReader(inputStream),GooglePlaceDetailResponse.class);
			
			return new LoaderResultOrException<GooglePlaceDetailResponse, Exception>(googlePlaceDetailResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<GooglePlaceDetailResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<GooglePlaceDetailResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<GooglePlaceDetailResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<GooglePlaceDetailResponse, Exception>(e);
		}
	}

}
