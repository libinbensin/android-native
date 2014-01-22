/**
 * 
 */
package com.android.foodbook.callbacks;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.android.foodbook.json.AutoCompleteResponse;
import com.android.foodbook.loader.AutoCompleteLoader;
import com.android.foodbook.loader.LoaderResultOrException;
import com.android.foodbook.model.AutoComplete;

/**
 * @author Libin_Salal
 *
 */
public class AutoCompleteCallback implements LoaderCallbacks<LoaderResultOrException<AutoCompleteResponse, Exception>>
{
	private Context mContext = null;
	
	private OnLoaderResultListener loaderResultListener;
	
	public AutoCompleteCallback(final Context argContext)
	{
		mContext = argContext;
	}
	
	@Override
	public Loader<LoaderResultOrException<AutoCompleteResponse, Exception>> onCreateLoader(
			int arg0, Bundle bundle) 
	{
		return new AutoCompleteLoader(mContext,bundle);
	}

	@Override
	public void onLoadFinished(
			Loader<LoaderResultOrException<AutoCompleteResponse, Exception>> argLoader,
			LoaderResultOrException<AutoCompleteResponse, Exception> argResult) {
		
		AutoCompleteResponse autoCompleteResponse = null;
		List<AutoComplete> autoCompletes = null;
		
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
			Loader<LoaderResultOrException<AutoCompleteResponse, Exception>> argLoader)
	{
		
	}
	
	public interface OnLoaderResultListener
	{
		public void handleResult(List<AutoComplete> argList);
	}
	
	public void setOnLoaderResultListener(OnLoaderResultListener listener)
	{
		loaderResultListener = listener;
	}
	
}
