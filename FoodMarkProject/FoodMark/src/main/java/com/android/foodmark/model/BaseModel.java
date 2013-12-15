package com.android.foodmark.model;

import java.io.Serializable;


public class BaseModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1316382890970714940L;

	private String modelName;
	
	private int modelId;
	
	private boolean isValid = true;
	
	public String getModelName() 
	{
		return modelName;
	}

	public void setModelName(String modelName) 
	{
		this.modelName = modelName;
	}

	public int getModelId() 
	{
		return modelId;
	}

	public void setModelId(int modelId) 
	{
		this.modelId = modelId;
	}

	public boolean isValid() 
	{
		return isValid;
	}

	public void setValid(boolean isValid) 
	{
		this.isValid = isValid;
	}
	
	
}
