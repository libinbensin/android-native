package com.android.foodmark.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.AppConstants;
import com.android.foodmark.constants.ConfigConstants;
import com.android.foodmark.dataaccess.HttpHelper;
import com.android.foodmark.json.GoogleAutoCompleteResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.os.Bundle;

public class GoogleAutoCompleteLoader extends BaseLoader<GoogleAutoCompleteResponse>
{
	private String mQuery;
	public GoogleAutoCompleteLoader(final Context argContext, Bundle argBundle) 
	{
		super(argContext);
		mQuery = argBundle.getString(AppConstants.QUERY);
	}
 
	@Override
	protected String getRequest() 
	{
		final String requestUrl = String.format(ConfigConstants.GOOGLE_PLACE_AUTOCOMPLETE_URL,mQuery, "true",MainApplication.getAppInstance().getGoogleApiKey());
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<GoogleAutoCompleteResponse, Exception> runOnBackground() 
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			GoogleAutoCompleteResponse autoCompleteResponse = new Gson().fromJson(new InputStreamReader(inputStream),GoogleAutoCompleteResponse.class);
			
			return new LoaderResultOrException<GoogleAutoCompleteResponse, Exception>(autoCompleteResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<GoogleAutoCompleteResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<GoogleAutoCompleteResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<GoogleAutoCompleteResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<GoogleAutoCompleteResponse, Exception>(e);
		}
	}

}
