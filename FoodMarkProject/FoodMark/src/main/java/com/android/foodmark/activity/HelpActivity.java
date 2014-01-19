package com.android.foodmark.activity;

import android.os.Bundle;
import android.view.Menu;

import com.android.foodmark.R;
import com.android.foodmark.fragment.HelpFragment;
import com.android.foodmark.fragment.SettingsFragment;

/**
 * Created by libin on 1/4/14.
 */
public class HelpActivity extends BaseActivity
{
    private HelpFragment mHelpFragment;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_base);

        if(savedInstance == null)
        {
            mHelpFragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_frame, mHelpFragment).commit();
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
