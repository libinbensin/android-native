package com.android.foodmark.activity;

import java.util.List;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.adapter.SearchCursorAdapter;
import com.android.foodmark.callbacks.GoogleAutoCompleteCallback;
import com.android.foodmark.callbacks.GoogleAutoCompleteCallback.OnLoaderResultListener;
import com.android.foodmark.constants.AppConstants;
import com.android.foodmark.fragment.GooglePlaceListFragment;
import com.android.foodmark.model.GoogleAutoComplete;
import com.android.foodmark.utils.AppGeoCoder;
import com.android.foodmark.utils.AppUtil;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class GooglePlaceListActivity extends BaseActivity implements OnQueryTextListener , OnLoaderResultListener , OnSuggestionListener
{
	private GooglePlaceListFragment placeListFragment = null;
	
	private SearchView mSearchView = null;
	private MenuItem mSearchItem = null;
	
	private static final String[] SYMBOL_COLUMS = {BaseColumns._ID, "text"};
	
	private GoogleAutoCompleteCallback autoCompleteCallback = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_list);
		if(savedInstanceState == null)
		{
			placeListFragment = new GooglePlaceListFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.search_listframe, placeListFragment).commit();
		}
		else
		{
			placeListFragment = (GooglePlaceListFragment) getSupportFragmentManager().findFragmentById(R.id.search_listframe);
		}
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	@Override
	protected boolean isHomeAsUpEnabled() 
	{
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		boolean handled = super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.action_spinner, menu);
		
		MenuItem spinnerItem = menu.findItem(R.id.menu_spinner);
		setUpFilterSpinner(spinnerItem);
		
		getMenuInflater().inflate(R.menu.action_search, menu);
		
		mSearchItem = menu.findItem(R.id.action_search);
		
		mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		MatrixCursor matrixCursor = new MatrixCursor(SYMBOL_COLUMS);
		SearchCursorAdapter searchCursorAdapter = new SearchCursorAdapter(this,matrixCursor);
		
		mSearchView.setSuggestionsAdapter(searchCursorAdapter);
		mSearchView.setOnSuggestionListener(this);
		mSearchView.setOnQueryTextListener(this);
		mSearchItem.setVisible(true);
		
		autoCompleteCallback = new GoogleAutoCompleteCallback(this);
		
		autoCompleteCallback.setOnLoaderResultListener(this);
		
		return handled;
	}
	
	private void setUpFilterSpinner(MenuItem menuItem) 
	{
		Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);
		
		SpinnerAdapter spinnerAdapter =  ArrayAdapter.createFromResource(this, R.array.spinner_filter_items, R.layout.spinner_menu_item);
		spinner.setAdapter(spinnerAdapter);
		spinner.setSelection(getDefaultSelectionIndex(), false);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) 
			{
				switch (position) 
				{
					case 0:
						refresh(getResources().getString(R.string.action_restaurant));
						return;
					case 1:
						refresh(getResources().getString(R.string.action_bar));
						return;
					case 2:
						refresh(getResources().getString(R.string.action_cafe));
						return;
					default:
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				
			}
			
		});
		
	}

	/**
	 * method to find the index based on the launch type in preference
	 * @return
	 */
	private int getDefaultSelectionIndex() 
	{
		String launchType = MainApplication.getPreferences().getString("TYPE",getResources().getString(R.string.action_restaurant));
		if(launchType.equals(getResources().getString(R.string.action_bar)))
		{
			return 1;
		}
		else if(launchType.equals(getResources().getString(R.string.action_cafe)))
		{
			return 2;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
	    {
			case R.id.action_search:
				((SearchView) MenuItemCompat.getActionView(item)).setIconified(false);
				return true;
		    default:
		    	return super.onOptionsItemSelected(item);
	    }
	}

	private void refresh(String argType) 
	{
		// refresh the view only if the launch type is changed
		if(!argType.equals(MainApplication.getPreferences().getString(AppConstants.TYPE, AppConstants.BLANK)))
		{
			MainApplication.getPreferences().edit().putString(AppConstants.TYPE,argType).commit();

			placeListFragment.fetchData(true);
		}
	}

	@Override
	public boolean onQueryTextChange(String s) 
	{
		if(AppUtil.hasValue(s))
		{
			Bundle bundle = new Bundle();
			bundle.putString(AppConstants.QUERY, s);
			getSupportLoaderManager().restartLoader(4, bundle, autoCompleteCallback);
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String s) 
	{
		return true;
	}

	@Override
	public void handleResult(List<GoogleAutoComplete> autoCompletes) 
	{
		if(autoCompletes != null && autoCompletes.size() >0)
		{
			MatrixCursor matrixCursor = new MatrixCursor(SYMBOL_COLUMS);
			
			mSearchView.getSuggestionsAdapter().changeCursor(matrixCursor);
			for(GoogleAutoComplete autoComplete : autoCompletes)
			{
				if(AppUtil.hasValue(autoComplete.getDescription()))
				{
					matrixCursor.addRow(new String[]{0 +"" ,autoComplete.getDescription()});
				}
			}
			mSearchView.getSuggestionsAdapter().notifyDataSetChanged();
		}
        
	}

	@Override
	public boolean onSuggestionClick(int position) 
	{
		Cursor cursor = (Cursor) mSearchView.getSuggestionsAdapter().getItem(position);
		String name = cursor.getString(1);
		// reset the search view and icon
		MenuItemCompat.collapseActionView(mSearchItem);
		
		Location searchLocation = AppGeoCoder.getLocation(this, name);
		if(searchLocation != null)
		{
			MainApplication.getAppInstance().setLocation(searchLocation);
			// refresh the list
			placeListFragment.fetchData(true);
		}
		else
		{
			// handle failure
			AppUtil.showToast(getResources().getString(R.string.location_error_message));
		}
		return true;
	}

	@Override
	public boolean onSuggestionSelect(int arg0) 
	{
		return false;
	}
}
