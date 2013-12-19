package com.android.foodmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.foodmark.R;
import com.android.foodmark.model.VideoDetail;

/**
 * Created by libin on 12/19/13.
 */
public class VideoListAdapter extends ArrayAdapter<VideoDetail>
{
    private Context mContext = null;

    public VideoListAdapter(Context context)
    {
        super(context, R.layout.device_video_list);
        mContext = context;
    }

    @Override
    public View getView(int position , View contentView , ViewGroup parent)
    {
        VideoDetail videoDetail = getItem(position);
        View row = contentView;
        VideoHolder videoHolder = null;

        if(row == null)
        {
            row = ((Activity)mContext).getLayoutInflater().inflate(R.layout.video_list_item, null);

            videoHolder = new VideoHolder();
            videoHolder.title = (TextView) row.findViewById(R.id.video_title);
            videoHolder.thumbnail = (ImageView) row.findViewById(R.id.video_item_thumbnail);
            row.setTag(videoHolder);
        }
        else
        {
            videoHolder = (VideoHolder) row.getTag();
        }

        if(videoDetail.getTitle() != null)
        {
            videoHolder.title.setText(videoDetail.getTitle());
        }

        if(videoDetail.getThumbnail() != null)
        {
            videoHolder.thumbnail.setImageBitmap(videoDetail.getThumbnail());
        }

        return row;
    }

    private static class VideoHolder
    {
        TextView title;
        ImageView thumbnail;
    }
}
