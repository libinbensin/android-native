package com.android.foodmark.activity;

import android.os.Bundle;
import android.view.Menu;

import com.android.foodmark.R;
import com.android.foodmark.fragment.VideoListFragment;

/**
 * Created by libin on 12/19/13.
 */
public class VideoListActivity extends BaseActivity
{
    private VideoListFragment mVideoListFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        if(savedInstanceState == null)
        {
            mVideoListFragment = new VideoListFragment();
            // add the fragment to fragment manager
            getSupportFragmentManager().beginTransaction().add(
                    R.id.video_list_frame,mVideoListFragment).commit();
        }
        else
        {
            mVideoListFragment = (VideoListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.video_list_frame);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}
