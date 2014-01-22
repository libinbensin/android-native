package com.android.foodbook.activity;

import android.os.Bundle;

import com.android.foodbook.R;
import com.android.foodbook.fragment.FavoriteListFragment;

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
