package com.android.foodmark.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.android.foodmark.MainApplication;
import com.android.foodmark.constants.AppConfig;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.dataaccess.HttpHelper;
import com.android.foodmark.json.AutoCompleteResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.os.Bundle;

public class AutoCompleteLoader extends BaseLoader<AutoCompleteResponse>
{
	private String mQuery;
	public AutoCompleteLoader(final Context argContext, Bundle argBundle)
	{
		super(argContext);
		mQuery = argBundle.getString(AppConstant.QUERY);
	}
 
	@Override
	protected String getRequest() 
	{
		final String requestUrl = String.format(AppConfig.GOOGLE_PLACE_AUTOCOMPLETE_URL,mQuery, "true",MainApplication.getAppInstance().getGoogleApiKey());
		return requestUrl;
	}

	@Override
	protected LoaderResultOrException<AutoCompleteResponse, Exception> runOnBackground()
	{
		try 
		{
			InputStream inputStream = HttpHelper.getInstance().executeGet(getRequest(), null, null);
			
			AutoCompleteResponse autoCompleteResponse = new Gson().fromJson(new InputStreamReader(inputStream),AutoCompleteResponse.class);
			
			return new LoaderResultOrException<AutoCompleteResponse, Exception>(autoCompleteResponse);
		}
		catch (JsonSyntaxException e)
		{
			return new LoaderResultOrException<AutoCompleteResponse, Exception>(e);
		}
		catch (JsonIOException e)
		{
			return new LoaderResultOrException<AutoCompleteResponse, Exception>(e);
		}
		catch (JsonParseException e)
		{
			return new LoaderResultOrException<AutoCompleteResponse, Exception>(e);
		}
		catch (IOException e) 
		{
			return new LoaderResultOrException<AutoCompleteResponse, Exception>(e);
		}
	}

}
