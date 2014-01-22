package com.android.foodbook.activity;

import com.android.foodbook.MainApplication;
import com.android.foodbook.R;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.database.FavoriteExecutor;
import com.android.foodbook.fragment.PlaceDetailFragment;
import com.android.foodbook.fragment.PlaceDetailFragment.OnResultLoadedListener;
import com.android.foodbook.model.PlaceList;
import com.android.foodbook.model.PlaceDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener;
import android.view.Menu;
import android.view.MenuItem;

public class PlaceDetailActivity extends BaseActivity implements OnResultLoadedListener
{

	private PlaceDetailFragment mPlaceDetailFragment;
	
	private ShareActionProvider mShareActionProvider;

    private PlaceList mPlaceList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_detail);

        mPlaceDetailFragment = new PlaceDetailFragment();
        mPlaceList = (PlaceList) getIntent().getSerializableExtra(AppConstant.PLACE);

		Bundle bundle = new Bundle();
		bundle.putString(AppConstant.REFERENCE, mPlaceList.getReference());
        mPlaceDetailFragment.setArguments(bundle);
			
		getSupportFragmentManager().beginTransaction()
                .add(R.id.search_detailframe, mPlaceDetailFragment).commit();

        mPlaceDetailFragment.setOnResultLoadedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_share, menu);
		
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);

	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);

        MenuItem favItem = menu.findItem(R.id.action_bookmark);
        //check if exist in favorite db
        boolean isFav = FavoriteExecutor.isFavorite(
                MainApplication.getAppInstance().getSQLiteInstance(), mPlaceList);
        if(isFav)
        {
            favItem.setIcon(android.R.drawable.btn_star_big_on);
        }
        else
        {
            favItem.setIcon(android.R.drawable.btn_star_big_off);
        }
        mPlaceList.setFavorite(isFav);

		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_bookmark:
            {
                if(mPlaceList.isFavorite())
                {
                    mPlaceList.setFavorite(false);
                    item.setIcon(android.R.drawable.btn_star_big_off);
                }
                else
                {
                    // set as favorite
                    mPlaceList.setFavorite(true);
                    item.setIcon(android.R.drawable.btn_star_big_on);
                }
                // update the data base
                FavoriteExecutor.setFavorite(
                        MainApplication.getAppInstance().getSQLiteInstance(), mPlaceList);
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
	
	/** Defines a default (dummy) share intent to initialize the action provider.
	  * However, as soon as the actual content to be used in the intent
	  * is known or changes, you must update the share intent by again calling
	  * mShareActionProvider.setShareIntent()
	  */
	private Intent getDefaultIntent(PlaceDetail argDetail)
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		
		if(argDetail != null)
		{
			stringBuilder.append(argDetail.getName());
			stringBuilder.append("\n");
			stringBuilder.append(argDetail.getAddress());
            stringBuilder.append("\n");
			intent.putExtra(Intent.EXTRA_TEXT,stringBuilder.toString());
		}
	    return intent;
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
	public void onResultData(PlaceDetail argDetail)
	{
		if(mShareActionProvider != null)
		{
			// set the information to be shared
			mShareActionProvider.setShareIntent(getDefaultIntent(argDetail));
		    mShareActionProvider.setOnShareTargetSelectedListener(
                    new OnShareTargetSelectedListener()
		    {
				@Override
				public boolean onShareTargetSelected(
                        ShareActionProvider actionProvider, Intent intent)
				{
					return true;
				}
			});
		}
	}
	
	
}
