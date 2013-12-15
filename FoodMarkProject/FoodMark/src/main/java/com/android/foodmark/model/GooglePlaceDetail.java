package com.android.foodmark.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GooglePlaceDetail extends BaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -275779125473749948L;
	
	private String name;
	
	@SerializedName("formatted_address")
	private String address;
	
	@SerializedName("formatted_phone_number")
	private String phonenumber;
	
	@SerializedName("icon")
	private String iconUrl;
	
	@SerializedName("international_phone_number")
	private String intPhoneNumber;
	
	private String id;
	
	private List<Photos> photos;
	
	
	public class Photos
	{
		private String height;
		@SerializedName("html_attributions")
		private List<String> HTTPAttributions;
		@SerializedName("photo_reference")
		private String reference;
		private String width;
		
		public String getHeight() {
			return height;
		}
		public void setHeight(String height) {
			this.height = height;
		}
		public List<String> getHTTPAttributions() {
			return HTTPAttributions;
		}
		public void setHTTPAttributions(List<String> hTTPAttributions) {
			HTTPAttributions = hTTPAttributions;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getWidth() {
			return width;
		}
		public void setWidth(String width) {
			this.width = width;
		}
	}

	private String rating;
	
	private String reference;
	
	private List<Reviews> reviews;
	
	public class Reviews
	{
		/*private Aspects aspects;
		
		public class Aspects
		{
			private String rating;
			private String type;
			public String getRating() {
				return rating;
			}
			public void setRating(String rating) {
				this.rating = rating;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}	
		}*/
		
		@SerializedName("author_name")
		private String authorName;
		@SerializedName("author_url")
		private String authorUrl;
		private String rating;
		private String text;
		private String time;

		/*public Aspects getAspects() {
			return aspects;
		}

		public void setAspects(Aspects aspects) {
			this.aspects = aspects;
		}*/

		public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}

		public String getAuthorUrl() {
			return authorUrl;
		}

		public void setAuthorUrl(String authorUrl) {
			this.authorUrl = authorUrl;
		}

		public String getRating() {
			return rating;
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}
	
	private List<String> types;
	
	private String url;
	
	@SerializedName("utc_offset")
	private String utcOffset;
	
	private String vicinity;
	
	private String website;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIntPhoneNumber() {
		return intPhoneNumber;
	}

	public void setIntPhoneNumber(String intPhoneNumber) {
		this.intPhoneNumber = intPhoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Photos> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(String utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	
	
}
