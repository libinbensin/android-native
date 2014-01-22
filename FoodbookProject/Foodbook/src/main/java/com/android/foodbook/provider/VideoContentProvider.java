package com.android.foodbook.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.foodbook.model.VideoDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 12/19/13.
 */
public class VideoContentProvider
{
    private Context mContext;

    public VideoContentProvider(final Context context)
    {
        mContext = context;
    }

    public List<VideoDetail> getDeviceVideos()
    {
        ContentResolver contentResolver = mContext.getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);

        List<VideoDetail> videoDetailList = new ArrayList<VideoDetail>();

        if(cursor == null)
        {
            // TODO: query failed. handle failure
            VideoDetail videoDetail = new VideoDetail();

            videoDetail.setTitle("unable to retrieve video from the device");
            videoDetailList.add(videoDetail);
        }
        else if(!cursor.moveToFirst())
        {
            // no media in the device

            VideoDetail videoDetail = new VideoDetail();

            videoDetail.setTitle("No Video in the device");
            videoDetailList.add(videoDetail);
        }
        else
        {
            int titleColumn = cursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            int idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);

            int idThumbnail = cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID);
            // traverse through the list
            do
            {
                VideoDetail videoDetail = new VideoDetail();

                String title = cursor.getString(titleColumn);
                long id = cursor.getLong(idColumn);
                Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(
                        mContext.getContentResolver(),cursor.getLong(idThumbnail),
                        MediaStore.Video.Thumbnails.MINI_KIND,null);

                videoDetail.setThumbnail(thumbnail);
                videoDetail.setTitle(title);
                videoDetail.setId(id);

                // add to the video array list
                videoDetailList.add(videoDetail);
            }
            while (cursor.moveToNext());
        }
        return videoDetailList;
    }
}
