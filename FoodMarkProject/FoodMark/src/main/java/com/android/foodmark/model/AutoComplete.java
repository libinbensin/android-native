package com.android.foodmark.model;

public class AutoComplete extends BaseModel
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5211481400633375350L;

	private String description;
	
	private String reference;
	
	private String id;
	
	/**
	 * @return the description
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * @return the reference
	 */
	public String getReference() 
	{
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) 
	{
		this.reference = reference;
	}

	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
}
