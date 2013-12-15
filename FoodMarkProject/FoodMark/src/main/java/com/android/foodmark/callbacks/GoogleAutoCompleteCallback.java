/**
 * 
 */
package com.android.foodmark.callbacks;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodmark.json.GoogleAutoCompleteResponse;
import com.android.foodmark.loader.GoogleAutoCompleteLoader;
import com.android.foodmark.loader.LoaderResultOrException;
import com.android.foodmark.model.GoogleAutoComplete;

/**
 * @author Libin_Salal
 *
 */
public class GoogleAutoCompleteCallback implements LoaderCallbacks<LoaderResultOrException<GoogleAutoCompleteResponse, Exception>>
{
	private Context mContext = null;
	
	private OnLoaderResultListener loaderResultListener;
	
	public GoogleAutoCompleteCallback(final Context argContext) 
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<GoogleAutoCompleteResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new GoogleAutoCompleteLoader(mContext,bundle);
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<GoogleAutoCompleteResponse, Exception>> argLoader,
			LoaderResultOrException<GoogleAutoCompleteResponse, Exception> argResult) {
		
		GoogleAutoCompleteResponse autoCompleteResponse = null;
		List<GoogleAutoComplete> autoCompletes = null;
		
		if( argResult != null && argResult.hasResult())
		{
			autoCompleteResponse = argResult.getResult();
		}
		
		if(autoCompleteResponse != null)
		{
			autoCompletes = autoCompleteResponse.getGoogleAutoSearchList();
		}

		loaderResultListener.handleResult(autoCompletes);
	}

	@Override
	public void onLoaderReset(
			Loader<LoaderResultOrException<GoogleAutoCompleteResponse, Exception>> argLoader) 
	{
		
	}
	
	public interface OnLoaderResultListener
	{
		public void handleResult(List<GoogleAutoComplete> argList);
	}
	
	public void setOnLoaderResultListener(OnLoaderResultListener listener)
	{
		loaderResultListener = listener;
	}
	
}
