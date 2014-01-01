package com.android.foodmark.json;

import java.util.List;

import com.android.foodmark.model.AutoComplete;
import com.google.gson.annotations.SerializedName;

public class AutoCompleteResponse
{
	@SerializedName("predictions")
	private List<AutoComplete> googleAutoSearchList;
	
	private String status;
	
	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public List<AutoComplete> getGoogleAutoSearchList()
	{
		return googleAutoSearchList;
	}

	public void setGoogleAutoSearchList(List<AutoComplete> googleAutoSearchs)
	{
		this.googleAutoSearchList = googleAutoSearchs;
	}
	
}
