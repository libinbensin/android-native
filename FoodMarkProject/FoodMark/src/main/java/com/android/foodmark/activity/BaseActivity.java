package com.android.foodmark.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppConstants;

public class BaseActivity extends ActionBarActivity
{

	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(final Bundle bundle)
	{
		super.onCreate(bundle);
		// initialize action bar
		initActionBar();
	}
	
	private void initActionBar()
	{
		getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());
	}
	
	protected boolean isHomeAsUpEnabled() 
	{
		return true;
	}

	protected boolean isLoadingEnabled()
	{
		return false;
	}

	public void showLoading()
	{
		if(progressDialog == null)
		{
			progressDialog = ProgressDialog.show(this, null,"Loading Please wait....");
		}
		else
		{
			progressDialog.show();
		}
		
	}
	
	public void hideLoading()
	{
		if(progressDialog != null)
		{
			progressDialog.hide();
		}
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
	protected void onResume()
	{
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	@Override
	protected void onDestroy()
	{
		if(progressDialog != null)
		{
			progressDialog.dismiss();
			progressDialog = null;
		}
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_common, menu);
				
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) 
		{
			case android.R.id.home:
			{
				finish();
				return true;
			}
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
}
