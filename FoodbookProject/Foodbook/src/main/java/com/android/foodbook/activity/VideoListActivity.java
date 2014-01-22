package com.android.foodbook.activity;

import android.os.Bundle;
import android.view.Menu;

import com.android.foodbook.R;
import com.android.foodbook.fragment.VideoListFragment;

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

        mVideoListFragment = new VideoListFragment();
            // add the fragment to fragment manager
        getSupportFragmentManager().beginTransaction().add(
                    R.id.video_list_frame,mVideoListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}
