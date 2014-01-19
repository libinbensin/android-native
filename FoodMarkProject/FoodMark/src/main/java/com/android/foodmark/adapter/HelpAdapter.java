package com.android.foodmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.foodmark.R;
import com.android.foodmark.model.HelpData;
import com.android.foodmark.model.SettingsData;

/**
 * Created by libin on 1/4/14.
 */
public class HelpAdapter extends ArrayAdapter<HelpData>
{
    private final Context mContext;

    public HelpAdapter(Context context)
    {
        super(context, R.layout.help_list);
        mContext = context;
    }

    @Override
    public boolean isEnabled (int position)
    {
        return false;
    }

    @Override
    public View getView(int position , View contentView, ViewGroup parent)
    {
        View row = contentView;
        HelpHolder mHelpHolder = null;

        if(row == null)
        {
            row = ((Activity)mContext).getLayoutInflater()
                    .inflate(R.layout.help_list_item, null);
            mHelpHolder = new HelpHolder();
            mHelpHolder.title = (TextView) row.findViewById(R.id.title);
            mHelpHolder.description = (TextView) row.findViewById(R.id.description);

            row.setTag(mHelpHolder);
        }
        else
        {
            mHelpHolder = (HelpHolder) row.getTag();
        }

        mHelpHolder.title.setText(getItem(position).getTitle());
        mHelpHolder.description.setText(getItem(position).getDescription());

        return row;
    }

    private static class HelpHolder
    {
        TextView title;
        TextView description;
    }
}
