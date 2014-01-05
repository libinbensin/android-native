package com.android.foodmark.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.android.foodmark.R;
import com.android.foodmark.fragment.SettingsFragment;

/**
 * Created by libin on 1/4/14.
 */
public class SettingsActivity extends BaseActivity
{
    private SettingsFragment mSettingsFragment;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_base);

        if(savedInstance == null)
        {
            mSettingsFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_frame, mSettingsFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // add only help menu here
        getMenuInflater().inflate(R.menu.action_common, menu);

        return true;
    }
}
