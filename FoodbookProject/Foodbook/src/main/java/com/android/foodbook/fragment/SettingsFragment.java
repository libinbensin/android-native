package com.android.foodbook.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodbook.R;
import com.android.foodbook.adapter.SettingsAdapter;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.model.SettingsData;
import com.android.foodbook.utils.AppUtil;

/**
 * Created by libin on 1/4/14.
 */
public class SettingsFragment extends ListFragment
{
    private static CharSequence mRadiusItems[] = new CharSequence[] {
            "5000" , "10000", "25000" , "50000"};

    private SettingsAdapter mSettingsAdapter;
    boolean gpsEnabled = false;

    @Override
    public void onActivityCreated(Bundle savedInstance)
    {
        super.onActivityCreated(savedInstance);
        mSettingsAdapter = new SettingsAdapter(getActivity());
        setListAdapter(mSettingsAdapter);
    }


    private void loadData()
    {
        // attach items
        // add set radius
        SettingsData data = new SettingsData();
        data.setTitle(getResources().getString(R.string.settings_radius_title));
        data.setDescription(AppUtil.getRadius() + AppConstant.SPACE + AppConstant.METERS);
        mSettingsAdapter.add(data);

        data = new SettingsData();
        data.setTitle(getResources().getString(R.string.settings_theme_title));
        data.setDescription(AppUtil.getTheme());
        mSettingsAdapter.add(data);

        data = new SettingsData();
        data.setTitle(AppConstant.LOCATION);
        // check if location is enabled
        if(AppUtil.isGPSEnabled())
        {
            data.setDescription(AppConstant.ENABLED);
            gpsEnabled = true;
        }
        else
        {
            data.setDescription(AppConstant.DISABLED);
            gpsEnabled = false;
        }
        mSettingsAdapter.add(data);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        // add/refresh the view
        mSettingsAdapter.clear();
        loadData();
    }

    @Override
    public void onListItemClick(ListView listView , View view , int position , long id)
    {
        switch (position)
        {
            case 0:
            {
                launchRadiusPicker();
                break;
            }
            case 1:
            {
                launchThemePicker();
                break;
            }
            case 2:
            {
                // launch location settings if GPS disabled
                if(!gpsEnabled)
                {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
                break;
            }
            default:
                break;
        }
    }

    private void launchRadiusPicker()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity().getActionBar().getThemedContext());

        // check the index of radius in preference
        builder.setSingleChoiceItems(
                mRadiusItems,getRadiusIndex(AppUtil.getRadius()),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int position)
            {
                // updated the selected radius to prefs
                AppUtil.setRadius(getRadiusString(position));
                mSettingsAdapter.getItem(0).setDescription(
                        getRadiusString(position) + AppConstant.SPACE + AppConstant.METERS);
                mSettingsAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(AppConstant.CANCEL, null);
        builder.setTitle(getResources().getString(R.string.select_radius_title));
        builder.show();
    }


    private int getRadiusIndex(String s)
    {
        int index = 0;

        for (CharSequence sequence : mRadiusItems)
        {
            if(sequence.toString().contentEquals(s))
            {
                break;
            }
            index++;
        }

        return index;
    }


    private String getRadiusString(int index)
    {
        if(index < 0 || index >= mRadiusItems.length)
        {
            // return default
            return mRadiusItems[0].toString();
        }
        return mRadiusItems[index].toString();
    }


    private void launchThemePicker()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity().getActionBar().getThemedContext());

        // check the index of theme in preference
        builder.setSingleChoiceItems(
                AppConstant.THEME_ITEMS,getThemeIndex(
                AppUtil.getTheme()),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int position)
            {
                // updated the selected radius to prefs
                AppUtil.setTheme(getThemeString(position));
                mSettingsAdapter.getItem(1).setDescription(
                        getThemeString(position));
                mSettingsAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
                AppUtil.showToast("Relaunch the app to reflect theme changes");
            }

        });

        builder.setNegativeButton(AppConstant.CANCEL, null);
        builder.setTitle(getResources().getString(R.string.select_theme_title));
        builder.show();
    }

    private int getThemeIndex(String theme)
    {
        int index = 0;

        for (CharSequence sequence : AppConstant.THEME_ITEMS)
        {
            if(sequence.toString().contentEquals(theme))
            {
                break;
            }
            index++;
        }

        return index;
    }


    private String getThemeString(int position)
    {
        return AppConstant.THEME_ITEMS[position].toString();
    }
}
