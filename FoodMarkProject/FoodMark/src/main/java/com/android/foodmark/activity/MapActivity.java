package com.android.foodmark.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppBundle;
import com.android.foodmark.fragment.PlaceMapFragment;
import com.android.foodmark.model.Geometry;

/**
 * Created by libin_000 on 12/31/13.
 */
public class MapActivity extends BaseActivity
{
    private Geometry mGeometry;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_default_layout);
        Bundle bundle = new Bundle();
        mGeometry = (Geometry) getIntent().getSerializableExtra(AppBundle.LOCATION);
        bundle.putSerializable(AppBundle.LOCATION, mGeometry);

        PlaceMapFragment mapFragment = new PlaceMapFragment();
        mapFragment.setArguments(bundle);

        getActionBar().hide();

        getSupportFragmentManager().beginTransaction().add(
                R.id.activity_frame,mapFragment).commit();

    }

}
