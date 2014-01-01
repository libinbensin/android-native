/**
 * 
 */
package com.android.foodmark.callbacks;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodmark.json.PlaceListResponse;
import com.android.foodmark.loader.LoaderResultOrException;
import com.android.foodmark.loader.PlaceListLoader;
import com.android.foodmark.model.PlaceList;

/**
 * @author Libin_Salal
 *
 */
public abstract class PlaceCallback implements LoaderCallbacks<LoaderResultOrException<PlaceListResponse, Exception>>
{
	private Context mContext = null;
	
	public PlaceCallback(final Context argContext)
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<PlaceListResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new PlaceListLoader(mContext,bundle);
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<PlaceListResponse, Exception>> argLoader,
			LoaderResultOrException<PlaceListResponse, Exception> argResult) {
		
		PlaceListResponse placeListResponse = null;
		List<PlaceList> placeList = null;
		
		if( argResult != null && argResult.hasResult())
		{
			placeListResponse = argResult.getResult();
		}
		
		if(placeListResponse != null)
		{
			placeList = placeListResponse.getPlaceLists();
		}
		
		onLoaderResult(placeList);
	}

	@Override
	public void onLoaderReset(
			Loader<LoaderResultOrException<PlaceListResponse, Exception>> argLoader)
	{
		
	}
	
	protected abstract void onLoaderResult(List<PlaceList> argPlaceList);
	
	
}
