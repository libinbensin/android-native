package com.android.foodmark.constants;

public final class ConfigConstants 
{
	public final static String GOOGLE_PLACE_TEXTSEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=%s&key=AIzaSyDQ5LSi13ighFRcp_JU7kXiKXnrjbPDiuk&sensor=true";
	
	public final static String GOOGLE_PLACE_AUTOCOMPLETE_EDIT_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&sensor=%s&types=geocode&key=%s&location=42.7575,71.4644&&radius=252500";

	public final static String GOOGLE_PLACE_AUTOCOMPLETE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&types=geocode&sensor=%s&key=%s";
	
	public final static String GOOGLE_PLACE_DETAIL_URL = "https://maps.googleapis.com/maps/api/place/details/json?reference=%s&sensor=%s&key=%s";

	public final static String GOOGLE_PLACE_RESTAURANT_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?types=%s&sensor=%s&key=%s&location=%s,%s&&radius=5000";
	
	public final static String GOOGLE_PLACE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=%s&maxheight=%s&photoreference=%s&sensor=true&key=%s";
	
}