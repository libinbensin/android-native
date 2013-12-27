package com.android.foodmark.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.foodmark.fragment.GooglePlaceListFragment;

/**
 * Created by libin on 12/26/13.
 */
public class PagerAdapter extends FragmentPagerAdapter
{

    public PagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position)
    {
        Bundle bundle = new Bundle();
        GooglePlaceListFragment placeListFragment = new GooglePlaceListFragment();
        switch (position)
        {
            case 0:
            {
                bundle.putString("TYPE","gym");
                break;
            }
            case 1:
            {
                bundle.putString("TYPE","health");
                break;
            }
            case 2:
            {
                bundle.putString("TYPE","spa");
                break;
            }
            default:
                break;

        }
        placeListFragment.setArguments(bundle);
        return placeListFragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
