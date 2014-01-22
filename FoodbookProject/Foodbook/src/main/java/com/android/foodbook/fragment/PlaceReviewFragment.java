package com.android.foodbook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class PlaceReviewFragment extends ListFragment
{
	public PlaceReviewFragment() 
	{
		
	}
	
	@Override
	public void onActivityCreated(Bundle bundle)
	{
		super.onActivityCreated(bundle);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);
	}
	
	@Override
    public void onAttach(Activity activity) 
	{
        super.onAttach(activity);
    }
	
	
}
