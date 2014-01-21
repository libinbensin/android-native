package com.android.foodmark.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.adapter.SearchCursorAdapter;
import com.android.foodmark.callbacks.AutoCompleteCallback;
import com.android.foodmark.callbacks.AutoCompleteCallback.OnLoaderResultListener;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.fragment.PlaceListFragment;
import com.android.foodmark.model.AutoComplete;
import com.android.foodmark.services.TimeOutService;
import com.android.foodmark.utils.AppGeoCoder;
import com.android.foodmark.utils.AppUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

import java.util.List;

public class PlaceListActivity extends BaseActivity
    implements OnQueryTextListener , OnLoaderResultListener , OnSuggestionListener ,
    GooglePlayServicesClient.ConnectionCallbacks ,GooglePlayServicesClient.OnConnectionFailedListener
{
	private PlaceListFragment placeListFragment = null;
	
	private SearchView mSearchView = null;
	private MenuItem mSearchItem = null;

	private static final String[] SYMBOL_COLUMS = {BaseColumns._ID, "text"};
	
	private AutoCompleteCallback autoCompleteCallback = null;

    private LocationClient mLocationClient = null;

    // default type is restaurant
    private String mLaunchType = null;

    private String mRadius = null;

    private boolean gpsEnabled = false;

    private AlertDialog mErrorDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
        // int app theme
        MainApplication.getAppInstance().intTheme();
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null)
		{
            mLocationClient = new LocationClient(this,this,this);
			placeListFragment = new PlaceListFragment();
			getSupportFragmentManager().beginTransaction().add(
                    R.id.activity_frame, placeListFragment).commit();
		}
        // get current lauch type in prefs
        mLaunchType = AppUtil.getLaunchType();
        // get current radius in prefs
        mRadius = AppUtil.getRadius();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

	@Override
	protected void onPause()
	{
        if(mErrorDialog != null && mErrorDialog.isShowing() )
        {
            mErrorDialog.dismiss();
        }
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
        // if gps location is disabled when activity first created
        // check again if enabled now
        if(!gpsEnabled)
        {
            if(AppUtil.isGPSEnabled())
            {
                mLocationClient.connect();
            }
            else
            {
                // show an alert dialog to user to enable it
                showGpsEnableDialog(getResources().getString(R.string.location_disabled_title),
                        getResources().getString(R.string.location_disabled_mg));
            }
        }
        else
        {
            // check if radius setting changed
            final String currentRadius = AppUtil.getRadius();
            if(mRadius != currentRadius)
            {
                mRadius = currentRadius;
                // reload the list
                placeListFragment.fetchData(mLaunchType,true);
            }
        }
	}

    private void showGpsEnableDialog(final String title, final String message)
    {
        if(mErrorDialog == null)
        {
            mErrorDialog = new AlertDialog.Builder(getActionBar().getThemedContext()).create();
            mErrorDialog.setCancelable(false);
            mErrorDialog.setTitle(title);
            mErrorDialog.setMessage(message);
            mErrorDialog.setButton(DialogInterface.BUTTON_POSITIVE, AppConstant.ENABLE,
                    new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });

            mErrorDialog.setButton(DialogInterface.BUTTON_NEGATIVE, AppConstant.CANCEL,
                        new DialogInterface.OnClickListener()
            {
                @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // dismiss the dialog and exit the app
                        dialogInterface.dismiss();
                        finish();
                    }
            });
        }
        mErrorDialog.show();
        ((TextView)mErrorDialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
    }


    private void showErrorDialog(final String title, final String message)
    {
        if(mErrorDialog == null)
        {
            mErrorDialog = new AlertDialog.Builder(
                    getActionBar().getThemedContext()).create();
            mErrorDialog.setCancelable(false);
            mErrorDialog.setTitle(title);
            mErrorDialog.setMessage(message);
            mErrorDialog.setButton(DialogInterface.BUTTON_POSITIVE, AppConstant.OK,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
        }
        mErrorDialog.show();
        ((TextView)mErrorDialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private boolean serviceConnected()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(ConnectionResult.SUCCESS == resultCode)
        {
            return true;
        }
        return false;
    }

    @Override
	protected boolean isHomeAsUpEnabled() 
	{
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.action_favorites, menu);

		getMenuInflater().inflate(R.menu.action_spinner, menu);
		
		MenuItem spinnerItem = menu.findItem(R.id.menu_spinner);
		setUpFilterSpinner(spinnerItem);
		
		getMenuInflater().inflate(R.menu.action_search, menu);
		
		mSearchItem = menu.findItem(R.id.action_search);
		
		mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MatrixCursor matrixCursor = new MatrixCursor(SYMBOL_COLUMS);
		SearchCursorAdapter searchCursorAdapter = new SearchCursorAdapter(
                getActionBar().getThemedContext(),matrixCursor);
		
		mSearchView.setSuggestionsAdapter(searchCursorAdapter);
		mSearchView.setOnSuggestionListener(this);
		mSearchView.setOnQueryTextListener(this);
		mSearchItem.setVisible(true);
		
		autoCompleteCallback = new AutoCompleteCallback(this);
		
		autoCompleteCallback.setOnLoaderResultListener(this);

		return super.onCreateOptionsMenu(menu);
	}
	
	private void setUpFilterSpinner(MenuItem menuItem) 
	{
		Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);
		
		SpinnerAdapter spinnerAdapter =  ArrayAdapter.createFromResource(
                getActionBar().getThemedContext(), R.array.spinner_filter_items, R.layout.spinner_menu_item);
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
		String launchType = AppUtil.getLaunchType();

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
		if(!argType.equals(MainApplication.getPreferences().getString(
                AppConstant.TYPE, AppConstant.BLANK)))
		{
            // write the selected launch type to prefs
			MainApplication.getPreferences().edit().putString(AppConstant.TYPE,argType).commit();
            // update the launch type
            mLaunchType =argType;
			placeListFragment.fetchData(mLaunchType,true);
		}
	}

	@Override
	public boolean onQueryTextChange(String s) 
	{
		if(AppUtil.hasValue(s))
		{
			Bundle bundle = new Bundle();
			bundle.putString(AppConstant.QUERY, s);
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
	public void handleResult(List<AutoComplete> autoCompletes)
	{
		if(autoCompletes != null && autoCompletes.size() >0)
		{
			MatrixCursor matrixCursor = new MatrixCursor(SYMBOL_COLUMS);
			
			mSearchView.getSuggestionsAdapter().changeCursor(matrixCursor);
			for(AutoComplete autoComplete : autoCompletes)
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
			MainApplication.getAppInstance().setSearchLocation(searchLocation);
			// refresh the list
			placeListFragment.fetchData(mLaunchType,true);
		}
		else
		{
			// handle failure
			AppUtil.showToast(getResources().getString(R.string.search_error_msg));
		}
		return true;
	}

	@Override
	public boolean onSuggestionSelect(int arg0) 
	{
		return false;
	}

    @Override
    public void onConnected(Bundle bundle)
    {
        if(serviceConnected())
        {
            Location location = mLocationClient.getLastLocation();
            if(location == null)
            {
                // ask user to check the location settings
                showErrorDialog(getResources().getString(R.string.location_error_title),
                        getResources().getString(R.string.location_error_msg));
                return;
            }
            MainApplication.getAppInstance().setLastLocation(location);
            MainApplication.getAppInstance().setSearchLocation(location);
            gpsEnabled = true;
            // update the UI
            placeListFragment.fetchData(mLaunchType,true);
        }

        if(mLocationClient != null)
        {
            mLocationClient.disconnect();
        }
    }

    @Override
    public void onDisconnected()
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        // ask user to check the location settings
        showErrorDialog(getResources().getString(R.string.location_error_msg),
                getResources().getString(R.string.location_error_msg));
    }

}
