package com.android.foodmark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.R;
import com.android.foodmark.activity.BaseActivity;
import com.android.foodmark.activity.PlaceDetailActivity;
import com.android.foodmark.adapter.PlaceListAdapter;
import com.android.foodmark.callbacks.PlaceCallback;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.model.Geometry;
import com.android.foodmark.model.PlaceList;
import com.android.foodmark.utils.AppLocationUtil;
import com.android.foodmark.utils.AppUtil;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PlaceListFragment extends ListFragment
{
	private PlaceListAdapter placeListAdapter;
    private String mLaunchType = null;

	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);
		placeListAdapter = new PlaceListAdapter(getActivity());
		setListAdapter(placeListAdapter);
		setListShown(true);
	}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    public void fetchData(String type, boolean argIsReload)
    {
        mLaunchType = type;
        fetchData(argIsReload);
    }

	private void fetchData(boolean argIsReload)
	{
		// show loading
        if(argIsReload)
        {
		    ((BaseActivity)getActivity()).showLoading();
        }
        Bundle bundle = new Bundle();
		bundle.putString(AppConstant.TYPE, AppUtil.toLowerCase(mLaunchType));
		
		PlaceCallback placeCallback =  new PlaceCallback(getActivity())
		{
			@Override
			protected void onLoaderResult(List<PlaceList> argGooglePlace)
			{

				placeListAdapter.clear();

				if(argGooglePlace != null && argGooglePlace.size() > 0)
				{
                    SortedMap<Double , PlaceList> placeMap = new TreeMap<Double, PlaceList>();

					for(PlaceList googlePlace : argGooglePlace)
					{
                        Geometry geometry = googlePlace.getGeometry();
						String dis = AppLocationUtil.getDistance(geometry.getLocation().getLat(),
                                geometry.getLocation().getLng());
						if(AppUtil.hasValue(dis))
						{
							googlePlace.setDistance(dis);
						}
                        placeMap.put(Double.valueOf(dis),googlePlace);
					}

					// iterate through the map
                    /*for(Map.Entry<Double,PlaceList> placeEntry : placeMap.entrySet())
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
		PlaceList placeList = placeListAdapter.getItem(position);

		Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(AppConstant.PLACE, placeList);

        startActivity(intent);
	}

	
}
