package com.android.foodmark.model;

public class Geometry 
{
	private AppLocation location;
	
	public class AppLocation
	{
		private String lat;
		private String lng;
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
	}
	
	public AppLocation getLocation() {
		return location;
	}

	public void setLocation(AppLocation location) {
		this.location = location;
	}
}
