package com.android.foodmark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.activity.BaseActivity;
import com.android.foodmark.activity.GooglePlaceDetailActivity;
import com.android.foodmark.adapter.PlaceListAdapter;
import com.android.foodmark.database.FavoriteExecutor;
import com.android.foodmark.model.GooglePlace;

import java.util.Iterator;
import java.util.SortedMap;

public class FavoriteListFragment extends ListFragment
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

    public void fetchData()
    {
        // check if available in database
        SortedMap<Double , GooglePlace> dbPlaceMap  =
                FavoriteExecutor.getAll(MainApplication.getAppInstance().getSQLiteInstance());

        if(dbPlaceMap != null && dbPlaceMap.size() >0)
        {
            Iterator iterator = dbPlaceMap.keySet().iterator();
            while (iterator.hasNext())
            {
                placeListAdapter.add(dbPlaceMap.get(iterator.next()));
            }
            ((BaseActivity)getActivity()).hideLoading();
        }
        else
        {
            setEmptyText(getResources().getString(R.string.empty_favorite_list));
        }
    }

    @Override
    public void onResume()
    {
        // favorite database might have changed ..refresh the list
        placeListAdapter.clear();
        fetchData();
        super.onResume();
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
}
