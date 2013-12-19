package com.android.foodmark.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.foodmark.model.AudioDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 12/19/13.
 */
public class AudioContentProvider
{
    private Context mContext;

    public AudioContentProvider(final Context context)
    {
        mContext = context;
    }

    public List<AudioDetail> getDeviceAudios()
    {
        ContentResolver contentResolver = mContext.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);

        List<AudioDetail> audioDetailList = new ArrayList<AudioDetail>();

        if(cursor == null)
        {
            // TODO: query failed. handle failure
            AudioDetail audioDetail = new AudioDetail();

            audioDetail.setTitle("unable to retrieve audio from the device");
            audioDetailList.add(audioDetail);
        }
        else if(!cursor.moveToFirst())
        {
            // no media in the device

            AudioDetail audioDetail = new AudioDetail();

            audioDetail.setTitle("No Audio in the device");
            audioDetailList.add(audioDetail);
        }
        else
        {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            // traverse through the list
            do
            {
                AudioDetail audioDetail = new AudioDetail();

                String title = cursor.getString(titleColumn);
                long id = cursor.getLong(idColumn);

                audioDetail.setTitle(title);
                audioDetail.setId(id);

                // add to the audio array list
                audioDetailList.add(audioDetail);
            }
            while (cursor.moveToNext());
        }
        return audioDetailList;
    }
}
