/**
 * 
 */
package com.android.foodmark.callbacks;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodmark.json.GooglePlaceResponse;
import com.android.foodmark.loader.LoaderResultOrException;
import com.android.foodmark.loader.GooglePlaceListLoader;
import com.android.foodmark.model.GooglePlace;

/**
 * @author Libin_Salal
 *
 */
public abstract class GooglePlaceCallback implements LoaderCallbacks<LoaderResultOrException<GooglePlaceResponse, Exception>>
{
	private Context mContext = null;
	
	public GooglePlaceCallback(final Context argContext) 
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<GooglePlaceResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new GooglePlaceListLoader(mContext,bundle);
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<GooglePlaceResponse, Exception>> argLoader,
			LoaderResultOrException<GooglePlaceResponse, Exception> argResult) {
		
		GooglePlaceResponse googlePlaceResponse = null;
		List<GooglePlace> googlePlace = null;
		
		if( argResult != null && argResult.hasResult())
		{
			googlePlaceResponse = argResult.getResult();
		}
		
		if(googlePlaceResponse != null)
		{
			googlePlace = googlePlaceResponse.getGooglePlaces();
		}
		
		onLoaderResult(googlePlace);
	}

	@Override
	public void onLoaderReset(
			Loader<LoaderResultOrException<GooglePlaceResponse, Exception>> argLoader) 
	{
		
	}
	
	protected abstract void onLoaderResult(List<GooglePlace> argGooglePlace);
	
	
}
