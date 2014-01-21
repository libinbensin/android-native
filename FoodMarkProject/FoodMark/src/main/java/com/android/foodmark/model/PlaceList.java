package com.android.foodmark.model;

import com.google.gson.annotations.SerializedName;

public class PlaceList extends BaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -275779125473749948L;
	
	@SerializedName("name")
	private String description;
	
	private String reference;
	
	private String icon;
	
	@SerializedName("price_level")
	private String pricelevel;
	
	private String rating;
	
	private String vicinity;
	
	private Geometry geometry;
	/** distance in miles */
	private String distance;

    private boolean favorite;

    @SerializedName("id")
    private String placeId;
	

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPricelevel() {
		return pricelevel;
	}

	public void setPricelevel(String pricelevel) {
		this.pricelevel = pricelevel;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}


    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public String getPlaceId()
    {
        return placeId;
    }

    public void setPlaceId(String placeId)
    {
        this.placeId = placeId;
    }
}
