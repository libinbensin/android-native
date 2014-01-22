package com.android.foodbook.activity;

import android.os.Bundle;

import com.android.foodbook.R;
import com.android.foodbook.constants.AppBundle;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.fragment.PlaceMapFragment;
import com.android.foodbook.model.Geometry;

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
        bundle.putString(AppBundle.MAP_TYPE, AppConstant.MAP_FULL);
        PlaceMapFragment mapFragment = new PlaceMapFragment();
        mapFragment.setArguments(bundle);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction().add(
                R.id.activity_frame,mapFragment).commit();

    }

}
