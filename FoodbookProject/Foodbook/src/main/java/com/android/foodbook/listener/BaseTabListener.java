package com.android.foodbook.listener;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by libin on 12/26/13.
 */
public class BaseTabListener implements ActionBar.TabListener
{
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<? extends Fragment> mClass;

    public BaseTabListener(Activity activity, String tag, Class<? extends Fragment> tClass)
    {
        mActivity = activity;
        mTag = tag;
        mClass = tClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        //check if fragment already initialized
        if(mFragment == null)
        {
            mFragment = Fragment.instantiate(mActivity,mClass.getName());
            fragmentTransaction.add(android.R.id.content, mFragment, mTag);
        }
        else
        {
            //if already exists
            fragmentTransaction.attach(mFragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        if(mFragment != null)
        {
            // detach the previous fragment , as the new one is attached
            fragmentTransaction.detach(mFragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // user reselected the selected tab.
    }
}
