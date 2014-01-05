package com.android.foodmark.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.android.foodmark.MainApplication;
import com.android.foodmark.adapter.SettingsAdapter;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.constants.AppPrefs;
import com.android.foodmark.model.SettingsData;
import com.android.foodmark.utils.AppUtil;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.da;

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
        data.setTitle("Search Radius");
        data.setDescription(AppUtil.getRadius() + AppConstant.SPACE + AppConstant.METERS); // default 5000
        mSettingsAdapter.add(data);

        data = new SettingsData();
        data.setTitle(AppConstant.LOCATION);
        // check if location is enabled
        LocationManager  mLocationManager = (LocationManager) MainApplication.getAppInstance()
                .getSystemService(Context.LOCATION_SERVICE);
        if(mLocationManager != null && mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER))
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
                // launch location settings if GPS disabled
                if(!gpsEnabled)
                {
                    Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
        builder.setTitle("Select Search Radius (In Meters)");
        builder.show();
    }


    private int getRadiusIndex(String s)
    {
        int index = 0;

        for (CharSequence sequence : mRadiusItems)
        {
            if(sequence.toString() == s)
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

}
