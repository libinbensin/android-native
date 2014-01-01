package com.android.foodmark.utils;

import java.io.IOException;
import java.util.List;

import com.android.foodmark.constants.AppConstant;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

public class AppGeoCoder 
{
	
	public static Location getLocation(Context context, String input)
	{
		Geocoder geocoder = new Geocoder(context);
		List<Address> addresses = null;
		Location location = new Location(AppConstant.BLANK);
		
		try 
		{
			addresses = geocoder.getFromLocationName(input, 1);
			location.setLatitude(addresses.get(0).getLatitude());
			location.setLongitude(addresses.get(0).getLongitude());
			
		} 
		catch (IOException e) {
		}
		
		return location;
	}
}
