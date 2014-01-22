package com.android.foodbook.json;

import com.android.foodbook.model.PlaceDetail;
import com.google.gson.annotations.SerializedName;

public class PlaceDetailResponse
{
	@SerializedName("result")
	private PlaceDetail placeDetail;
	
	private String status;

	
	public PlaceDetail getPlaceDetail() {
		return placeDetail;
	}

	public void setPlaceDetail(PlaceDetail placeDetail) {
		this.placeDetail = placeDetail;
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
