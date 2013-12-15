/**
 * 
 */
package com.android.foodmark.callbacks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodmark.json.GooglePlaceDetailResponse;
import com.android.foodmark.loader.GooglePlaceDetailLoader;
import com.android.foodmark.loader.LoaderResultOrException;
import com.android.foodmark.model.GooglePlaceDetail;

/**
 * @author Libin_Salal
 *
 */
public abstract class GooglePlaceDetailCallback implements LoaderCallbacks<LoaderResultOrException<GooglePlaceDetailResponse, Exception>>
{

	private Context mContext = null;
	
	public GooglePlaceDetailCallback(final Context argContext) 
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<GooglePlaceDetailResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new GooglePlaceDetailLoader(mContext,bundle.getString("REFERENCE"));
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<GooglePlaceDetailResponse, Exception>> argLoader,
			LoaderResultOrException<GooglePlaceDetailResponse, Exception> argResult) {
		
		GooglePlaceDetailResponse googlePlaceResponse = null;
		GooglePlaceDetail googlePlaceDetail = null;
		
		if( argResult != null && argResult.hasResult())
		{
			googlePlaceResponse = argResult.getResult();
		}
		
		if(googlePlaceResponse != null)
		{
			googlePlaceDetail = googlePlaceResponse.getGooglePlaceDetail();
		}
		
		onLoaderResult(googlePlaceDetail);
	}

	@Override
	public void onLoaderReset(
			Loader<LoaderResultOrException<GooglePlaceDetailResponse, Exception>> argLoader) 
	{
		
	}
	
	protected abstract void onLoaderResult(GooglePlaceDetail argGooglePlaceDetail);
	
	
}
