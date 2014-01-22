package com.android.foodbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodbook.MainApplication;
import com.android.foodbook.R;
import com.android.foodbook.activity.BaseActivity;
import com.android.foodbook.activity.PlaceDetailActivity;
import com.android.foodbook.adapter.PlaceListAdapter;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.database.FavoriteExecutor;
import com.android.foodbook.model.PlaceList;

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
        SortedMap<Double , PlaceList> dbPlaceMap  =
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
        PlaceList placeList = placeListAdapter.getItem(position);

        Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
        intent.putExtra(AppConstant.PLACE, placeList);

        startActivity(intent);
    }
}
