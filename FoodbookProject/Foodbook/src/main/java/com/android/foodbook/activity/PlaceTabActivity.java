package com.android.foodbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.android.foodbook.R;
import com.android.foodbook.adapter.PagerAdapter;
import com.android.foodbook.fragment.PlaceListFragment;
import com.android.foodbook.listener.SwipeBaseTabListener;

/**
 * Created by libin_000 on 12/26/13.
 */
public class PlaceTabActivity extends BaseTabActivity
{
    private PagerAdapter mStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle onSavedInstance)
    {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_view_pager);

        mStatePagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(mStatePagerAdapter);

        Bundle bundle = new Bundle();

        bundle.putString("TITLE","gym" );
        addTab(bundle, PlaceListFragment.class);

        bundle.putString("TITLE", "health");
        addTab(bundle, PlaceListFragment.class);

        bundle.putString("TITLE", "spa");
        addTab(bundle, PlaceListFragment.class);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void addTab(Bundle bundle, Class<? extends Fragment> cls)
    {
        String title = bundle.getString("TITLE");
        int icon =  bundle.getInt("ICON");

        ActionBar.Tab tab =  getSupportActionBar().newTab();

        Intent intent = new Intent(this,cls);

        // set tab title
        tab.setText(title);
        // set tab icon
        if(icon >0) tab.setIcon(icon);
        // set tab listener
        tab.setTabListener(new SwipeBaseTabListener(this,mViewPager, title, cls));
        getSupportActionBar().addTab(tab);
    }

}
