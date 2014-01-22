package com.android.foodbook.activity;

import android.os.Bundle;
import android.view.Menu;

import com.android.foodbook.R;
import com.android.foodbook.fragment.SettingsFragment;

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
