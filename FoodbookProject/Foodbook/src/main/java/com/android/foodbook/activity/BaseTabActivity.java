package com.android.foodbook.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar.Tab;

import com.android.foodbook.listener.BaseTabListener;

/**
 * Created by libin on 12/26/13.
 */
public class BaseTabActivity extends BaseActivity
{
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        // set content view is not used here.. because we use the root android.id.content
        // as the container for each fragment
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void addTab(Bundle bundle, Class<? extends Fragment> cls)
    {
        String title = bundle.getString("TITLE");
        int icon =  bundle.getInt("ICON");

        Tab tab =  getSupportActionBar().newTab();

        Intent intent = new Intent(this,cls);

        // set tab title
        tab.setText(title);
        // set tab icon
        if(icon >0) tab.setIcon(icon);
        // set tab listener
        tab.setTabListener(new BaseTabListener(this, title, cls));
        getSupportActionBar().addTab(tab);
    }


}
