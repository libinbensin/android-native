package com.android.foodmark.activity;

import android.os.Bundle;

import com.android.foodmark.R;
import com.android.foodmark.fragment.FavoriteListFragment;

/**
 * Created by libin on 12/24/13.
 */
public class FavoriteActivity extends BaseActivity
{
    private FavoriteListFragment mFavoriteListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
        {
            mFavoriteListFragment = new FavoriteListFragment();
            getSupportFragmentManager().beginTransaction().add(
                    R.id.activity_frame, mFavoriteListFragment).commit();
        }
    }
}
