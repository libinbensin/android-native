package com.android.foodbook.activity;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.VideoView;

import com.android.foodbook.R;

/**
 * Created by libin on 12/19/13.
 */
public class VideoDetailActivity extends BaseActivity
{
    private VideoView mVideoView = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        mVideoView = (VideoView) findViewById(R.id.device_video_view);
        long videoId = getIntent().getExtras().getLong("VIDEO_ID");
        Uri contentUri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,videoId);
        mVideoView.setVideoURI(contentUri);
        mVideoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}
