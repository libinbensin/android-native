package com.android.foodmark.listener;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

/**
 * Created by libin on 12/26/13.
 */
public class SwipeTabListener extends TabListener
{
    private final ViewPager mViewPager;

    public SwipeTabListener(Activity activity,ViewPager viewPager,
                            String tag, Class<? extends Fragment> tClass)
    {
        super(activity, tag, tClass);
        mViewPager = viewPager;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab , FragmentTransaction fragmentTransaction)
    {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {

    }
}
