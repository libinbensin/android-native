package com.android.foodmark.fragment;

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
		setListShown(true);
	}
	
	@Override
    public void onAttach(Activity activity) 
	{
        super.onAttach(activity);
    }
	
	
}
