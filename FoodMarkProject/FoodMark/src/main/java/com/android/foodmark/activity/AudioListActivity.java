package com.android.foodmark.activity;

import android.os.Bundle;
import android.view.Menu;

import com.android.foodmark.R;
import com.android.foodmark.fragment.AudioListFragment;

/**
 * Created by libin on 12/19/13.
 */
public class AudioListActivity extends BaseActivity
{
    private AudioListFragment mAudioListFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        mAudioListFragment = new AudioListFragment();
            // add the fragment to fragment manager
            getSupportFragmentManager().beginTransaction().add(
                    R.id.audio_listframe,mAudioListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}
