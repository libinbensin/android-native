package com.android.foodmark.json;

import java.util.List;

import com.android.foodmark.model.GoogleAutoComplete;
import com.google.gson.annotations.SerializedName;

public class GoogleAutoCompleteResponse 
{
	@SerializedName("predictions")
	private List<GoogleAutoComplete> googleAutoSearchList;
	
	private String status;
	
	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public List<GoogleAutoComplete> getGoogleAutoSearchList() 
	{
		return googleAutoSearchList;
	}

	public void setGoogleAutoSearchList(List<GoogleAutoComplete> googleAutoSearchs) 
	{
		this.googleAutoSearchList = googleAutoSearchs;
	}
	
}
