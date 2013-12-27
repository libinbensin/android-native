package com.android.foodmark.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppConstants;

/**
 * Created by libin on 12/21/13.
 */
public class DrawerActivity extends BaseActivity
{
    private DrawerLayout mDrawerLayout = null;
    private ListView mDrawerList = null;
    private String[] mDrawerItems;
    private ActionBarDrawerToggle mDrawerToggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerItems = getResources().getStringArray(R.array.left_drawer_array);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow , GravityCompat.START);

        mDrawerList.setAdapter(new ArrayAdapter<String>(
                this,R.layout.drawer_list_item,mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,R.drawable.ic_drawer,
                R.string.drawer_open,R.string.drawer_close)
        {
            public void onDrawerOpened(View view)
            {
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view)
            {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        for(int index = 0 ; index < menu.size() ; index ++)
        {
            MenuItem menuItem = menu.getItem(index);
            if(menuItem != null)
            {
                // hide the menu items if the drawer is open
                menuItem.setVisible(!drawerOpen);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                {
                    Intent intent = new Intent(DrawerActivity.this, PlaceTabActivity.class);
                    startActivity(intent);
                    break;
                }
                case 1:
                {
                    Intent intent = new Intent(DrawerActivity.this , AudioListActivity.class);
                    startActivity(intent);
                    break;
                }
                case 2:
                {
                    Intent intent = new Intent(DrawerActivity.this,VideoListActivity.class);
                    startActivity(intent);
                    break;
                }
                case 3:
                {
                    Intent intent = new Intent(DrawerActivity.this,CameraActivity.class);
                    intent.putExtra("MEDIA_TYPE", AppConstants.MEDIA_TYPE_IMAGE);
                    startActivity(intent);
                    break;
                }
                case 4:
                {
                    Intent intent = new Intent(DrawerActivity.this,CameraActivity.class);
                    intent.putExtra("MEDIA_TYPE", AppConstants.MEDIA_TYPE_VIDEO);
                    startActivity(intent);
                    break;
                }
                case 5:
                {
                    Intent intent = new Intent(DrawerActivity.this,FavoriteActivity.class);
                    startActivity(intent);
                    break;
                }
                case 6:
                    break;
                case 7:
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}
