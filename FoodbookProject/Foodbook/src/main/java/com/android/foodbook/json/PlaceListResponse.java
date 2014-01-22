package com.android.foodbook.json;

import java.util.List;

import com.android.foodbook.model.PlaceList;
import com.google.gson.annotations.SerializedName;

public class PlaceListResponse
{
	@SerializedName("results")
	private List<PlaceList> placeLists;
	
	private String status;

	public List<PlaceList> getPlaceLists()
	{
		return placeLists;
	}

	public void setPlaceLists(List<PlaceList> placeLists)
	{
		this.placeLists = placeLists;
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
