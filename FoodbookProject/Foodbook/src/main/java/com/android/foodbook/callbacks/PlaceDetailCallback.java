/**
 * 
 */
package com.android.foodbook.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodbook.json.PlaceDetailResponse;
import com.android.foodbook.loader.PlaceDetailLoader;
import com.android.foodbook.loader.LoaderResultOrException;
import com.android.foodbook.model.PlaceDetail;

/**
 * @author Libin_Salal
 *
 */
public abstract class PlaceDetailCallback implements LoaderCallbacks<LoaderResultOrException<PlaceDetailResponse, Exception>>
{

	private Context mContext = null;
	
	public PlaceDetailCallback(final Context argContext)
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<PlaceDetailResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new PlaceDetailLoader(mContext,bundle.getString("REFERENCE"));
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<PlaceDetailResponse, Exception>> argLoader,
			LoaderResultOrException<PlaceDetailResponse, Exception> argResult) {
		
		PlaceDetailResponse googlePlaceResponse = null;
		PlaceDetail placeDetail = null;
		
		if( argResult != null && argResult.hasResult())
		{
			googlePlaceResponse = argResult.getResult();
		}
		
		if(googlePlaceResponse != null)
		{
			placeDetail = googlePlaceResponse.getPlaceDetail();
		}
		
		onLoaderResult(placeDetail);
	}

	@Override
	public void onLoaderReset(
			Loader<LoaderResultOrException<PlaceDetailResponse, Exception>> argLoader)
	{
		
	}
	
	protected abstract void onLoaderResult(PlaceDetail argPlaceDetail);
	
	
}
