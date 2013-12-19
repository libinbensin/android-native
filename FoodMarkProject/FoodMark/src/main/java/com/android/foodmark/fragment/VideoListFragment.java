package com.android.foodmark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.activity.VideoDetailActivity;
import com.android.foodmark.adapter.VideoListAdapter;
import com.android.foodmark.model.VideoDetail;
import com.android.foodmark.provider.VideoContentProvider;

import java.util.List;

/**
 * Created by libin on 12/19/13.
 */
public class VideoListFragment extends ListFragment
{
    private VideoListAdapter mVideoListAdapter = null;
    private VideoContentProvider mVideoContentProvider = null;

    @Override
    public void onActivityCreated(Bundle onSavedInstance)
    {
        super.onActivityCreated(onSavedInstance);
        mVideoListAdapter = new VideoListAdapter(getActivity());
        setListAdapter(mVideoListAdapter);
        fetchData();
    }

    private void fetchData()
    {
        List<VideoDetail> videoDetailList = null;

        if(mVideoContentProvider == null)
        {
            mVideoContentProvider = new VideoContentProvider(getActivity());
        }
        videoDetailList = mVideoContentProvider.getDeviceVideos();
        mVideoListAdapter.addAll(videoDetailList);
    }

    @Override
    public void onListItemClick(ListView listView , View view , int position , long id )
    {
        VideoDetail videoDetail = mVideoListAdapter.getItem(position);
        long videoId = videoDetail.getId();

        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra("VIDEO_ID",videoId);
        startActivity(intent);
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
