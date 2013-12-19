package com.android.foodmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.foodmark.R;
import com.android.foodmark.model.AudioDetail;

/**
 * Created by libin on 12/19/13.
 */
public class AudioListAdapter extends ArrayAdapter<AudioDetail>
{
    private Context mContext = null;

    public AudioListAdapter(Context context)
    {
        super(context, R.layout.device_audio_list);
        mContext = context;
    }

    @Override
    public View getView(int position , View contentView , ViewGroup parent)
    {
        AudioDetail audioDetail = getItem(position);
        View row = contentView;
        AudioHolder audioHolder = null;

        if(row == null)
        {
            row = ((Activity)mContext).getLayoutInflater().inflate(R.layout.audio_list_item, null);

            audioHolder = new AudioHolder();
            audioHolder.title = (TextView) row.findViewById(R.id.audio_title);

            row.setTag(audioHolder);
        }
        else
        {
            audioHolder = (AudioHolder) row.getTag();
        }

        if(audioDetail.getTitle() != null)
        {
            audioHolder.title.setText(audioDetail.getTitle());
        }

        return row;
    }

    private static class AudioHolder
    {
        TextView title;
    }
}
