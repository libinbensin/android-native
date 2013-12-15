package com.android.foodmark.json;

import com.android.foodmark.model.GooglePlaceDetail;
import com.google.gson.annotations.SerializedName;

public class GooglePlaceDetailResponse 
{
	@SerializedName("result")
	private GooglePlaceDetail googlePlaceDetail;
	
	private String status;

	
	public GooglePlaceDetail getGooglePlaceDetail() {
		return googlePlaceDetail;
	}

	public void setGooglePlaceDetail(GooglePlaceDetail googlePlaceDetail) {
		this.googlePlaceDetail = googlePlaceDetail;
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
