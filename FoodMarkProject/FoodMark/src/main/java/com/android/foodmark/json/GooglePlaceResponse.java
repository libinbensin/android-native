package com.android.foodmark.json;

import java.util.List;

import com.android.foodmark.model.GooglePlace;
import com.google.gson.annotations.SerializedName;

public class GooglePlaceResponse 
{
	@SerializedName("results")
	private List<GooglePlace> googlePlaces;
	
	private String status;

	public List<GooglePlace> getGooglePlaces() 
	{
		return googlePlaces;
	}

	public void setGooglePlaces(List<GooglePlace> googlePlaces) 
	{
		this.googlePlaces = googlePlaces;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}
	
}
