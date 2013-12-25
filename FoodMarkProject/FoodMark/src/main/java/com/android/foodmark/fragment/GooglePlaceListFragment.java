package com.android.foodmark.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.activity.BaseActivity;
import com.android.foodmark.activity.GooglePlaceDetailActivity;
import com.android.foodmark.adapter.PlaceListAdapter;
import com.android.foodmark.callbacks.GooglePlaceCallback;
import com.android.foodmark.model.GooglePlace;
import com.android.foodmark.utils.AppUtil;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class GooglePlaceListFragment extends ListFragment
{
	private PlaceListAdapter placeListAdapter;
	
	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);
		placeListAdapter = new PlaceListAdapter(getActivity());
		setListAdapter(placeListAdapter);
		setListShown(true);
	}

	public void fetchData(boolean argIsReload)
	{
		// show loading
		((BaseActivity)getActivity()).showLoading();

        String launchType = MainApplication.getPreferences().getString("TYPE",getResources().getString(R.string.action_restaurant));
		Bundle bundle = new Bundle();
		bundle.putString("TYPE", AppUtil.toLowerCase(launchType));
		
		GooglePlaceCallback placeCallback =  new GooglePlaceCallback(getActivity()) 
		{
			@Override
			protected void onLoaderResult(List<GooglePlace> argGooglePlace)
			{

				placeListAdapter.clear();

				if(argGooglePlace != null && argGooglePlace.size() > 0)
				{
                    SortedMap<Double , GooglePlace> placeMap = new TreeMap<Double, GooglePlace>();


					for(GooglePlace googlePlace : argGooglePlace)
					{
						String dis = getDistance(googlePlace);
						if(AppUtil.hasValue(dis))
						{
							googlePlace.setDistance(dis);
						}
                        placeMap.put(Double.valueOf(dis),googlePlace);
					}

					// iterate through the map
                    /*for(Map.Entry<Double,GooglePlace> placeEntry : placeMap.entrySet())
                    {
                        placeListAdapter.add(placeEntry.getValue());
                    }*/
                    Iterator iterator = placeMap.keySet().iterator();
                    while (iterator.hasNext())
                    {
                        placeListAdapter.add(placeMap.get(iterator.next()));
                    }
				}
				else
				{
					setEmptyText(getString(R.string.place_empty_list));
				}
				((BaseActivity)getActivity()).hideLoading();
				
			}
			
		};
		
		if(argIsReload)
		{
			getLoaderManager().restartLoader(1, bundle,placeCallback);
		}
		else
		{	
			getLoaderManager().initLoader(1, bundle,placeCallback);
		}
	}
	
	
	@Override
	public void onListItemClick (ListView l, View v, int position, long id)
	{
		GooglePlace googlePlace = placeListAdapter.getItem(position);
		String selectedRef =  googlePlace.getReference();
		
		Intent intent = new Intent(getActivity(), GooglePlaceDetailActivity.class);
        intent.putExtra("PLACE",googlePlace);
		startActivity(intent);
	}
	
	/**
	 * method to calculate distance from current selected location
	 * 
	 */
	private String getDistance(GooglePlace place) 
	{
		Double toLat = Double.valueOf(place.getGeometry().getLocation().getLat());
		Double toLng = Double.valueOf(place.getGeometry().getLocation().getLng());
		
		Location from = MainApplication.getAppInstance().getLocation();
		float[] results = {0 };
		
		Location.distanceBetween(from.getLatitude(), from.getLongitude(), toLat, toLng, results);
		
		Double f = results[0] * 0.00062137119;
		
		return new DecimalFormat("#.##").format(f);
	}
	
}
