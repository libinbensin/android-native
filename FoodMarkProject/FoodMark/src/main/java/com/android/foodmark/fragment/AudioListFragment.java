package com.android.foodmark.fragment;

import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.adapter.AudioListAdapter;
import com.android.foodmark.model.AudioDetail;
import com.android.foodmark.provider.AudioContentProvider;

import java.io.IOException;
import java.util.List;

/**
 * Created by libin on 12/19/13.
 */
public class AudioListFragment extends ListFragment implements MediaPlayer.OnPreparedListener
{
    private AudioListAdapter mAudioListAdapter = null;
    private AudioContentProvider mAudioContentProvider = null;

    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    public void onActivityCreated(Bundle onSavedInstance)
    {
        super.onActivityCreated(onSavedInstance);
        mAudioListAdapter = new AudioListAdapter(getActivity());
        setListAdapter(mAudioListAdapter);
        fetchData();
    }

    private void fetchData()
    {
        List<AudioDetail> audioDetailList = null;

        if(mAudioContentProvider == null)
        {
            mAudioContentProvider = new AudioContentProvider(getActivity());
        }
        audioDetailList = mAudioContentProvider.getDeviceAudios();
        mAudioListAdapter.addAll(audioDetailList);
    }

    @Override
    public void onListItemClick(ListView listView , View view , int position , long id )
    {
        AudioDetail audioDetail = mAudioListAdapter.getItem(position);
        long audioId = audioDetail.getId();

        // stop the previous playing audio
        if(mMediaPlayer != null && mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
        }
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioId);
        try
        {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getActivity().getApplicationContext(),uri);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(this);
        }
        catch (IOException e)
        {
            // handle failure and show error message to user
            return;
        }
    }

    @Override
    public void onStop()
    {
        if(mMediaPlayer != null && mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        if(mMediaPlayer != null)
        {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        mMediaPlayer.start();
    }
}
