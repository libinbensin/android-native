package com.android.foodmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.foodmark.R;
import com.android.foodmark.model.SettingsData;

/**
 * Created by libin on 1/4/14.
 */
public class SettingsAdapter extends ArrayAdapter<SettingsData>
{
    private final Context mContext;

    public SettingsAdapter(Context context)
    {
        super(((Activity)context).getActionBar().getThemedContext(), R.layout.settings_list);
        mContext = context;
    }

    @Override
    public View getView(int position , View contentView, ViewGroup parent)
    {
        View row = contentView;
        SettingsHolder mSettingsHolder = null;

        if(row == null)
        {
            row = ((Activity)mContext).getLayoutInflater()
                    .inflate(R.layout.settings_list_item, null);
            mSettingsHolder = new SettingsHolder();
            mSettingsHolder.title = (TextView) row.findViewById(R.id.title);
            mSettingsHolder.description = (TextView) row.findViewById(R.id.description);

            row.setTag(mSettingsHolder);
        }
        else
        {
            mSettingsHolder = (SettingsHolder) row.getTag();
        }

        mSettingsHolder.title.setText(getItem(position).getTitle());
        mSettingsHolder.description.setText(getItem(position).getDescription());

        return row;
    }

    private static class SettingsHolder
    {
        TextView title;
        TextView description;
    }
}
