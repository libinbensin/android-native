package com.android.foodmark.fragment;

import android.os.Bundle;

import com.android.foodmark.constants.AppBundle;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.model.Geometry;
import com.android.foodmark.utils.AppUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by libin on 12/31/13.
 */
public class PlaceMapFragment extends SupportMapFragment
{
    private GoogleMap mGoogleMap;
    private Geometry mGeometry;
    private String mType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mGeometry = (Geometry) getArguments().getSerializable(AppBundle.LOCATION);
        mType = getArguments().getString(AppBundle.MAP_TYPE);
    }

    @Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

        mGoogleMap = getMap();

        if(mType.contentEquals(AppConstant.MAP_SIMPLE))
        {
            mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
            mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        Geometry.AppLocation location = mGeometry.getLocation();

        LatLng latLng = new LatLng(
                AppUtil.toDouble(location.getLat()),AppUtil.toDouble(location.getLng()));

        markerOptions.position(latLng);

        Marker marker = mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        //mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
