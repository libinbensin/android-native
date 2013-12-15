package com.android.foodmark.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.foodmark.MainApplication;
import com.android.foodmark.R;
import com.android.foodmark.model.GooglePlace;
import com.android.foodmark.utils.AppUtil;
import com.android.foodmark.widgets.StarRatingWidget;

public class PlaceListAdapter extends ArrayAdapter<GooglePlace>
{
	private Activity mContext;
	
	public PlaceListAdapter(Activity argContext)
	{
		super(argContext, R.layout.place_list);
		mContext = argContext;
	}
	
	@Override
	public View getView(int position, View contentView , ViewGroup parent)
	{
		GooglePlace googlePlace = getItem(position);
		
		View row = contentView;
		PlaceHolder placeHolder = null;
		
		if(row == null)
		{
			row = mContext.getLayoutInflater().inflate(R.layout.place_list_item, null);
			placeHolder = new PlaceHolder();
			placeHolder.icon = (ImageView) row.findViewById(R.id.search_item_icon);
			placeHolder.title =  (TextView) row.findViewById(R.id.search_text);
			placeHolder.distance =  (TextView) row.findViewById(R.id.distance);
			placeHolder.vicinity = (TextView) row.findViewById(R.id.vicinity);
			placeHolder.linearLayout = (LinearLayout) row.findViewById(R.id.star_rating_layout);
			
			row.setTag(placeHolder);
		}
		else
		{
			placeHolder = (PlaceHolder) row.getTag();
		}
		
		// draw icon
		if(googlePlace.getIcon() != null)
		{
			// set animation
			AppUtil.setImageAnimation(placeHolder.icon, 1000);
			MainApplication.getImageDownloader().download(googlePlace.getIcon(), placeHolder.icon);
		}
		
		if(googlePlace.getDescription() != null)
		{
			placeHolder.title.setText(googlePlace.getDescription());
		}
		if(googlePlace.getDistance() != null)
		{
			placeHolder.distance.setText("Distance : " + googlePlace.getDistance() + " miles");
		}
		
		if(googlePlace.getRating() != null)
		{
			StarRatingWidget starRatingWidget = new StarRatingWidget(mContext);
			placeHolder.linearLayout.removeAllViews();
			starRatingWidget.setStarRating(placeHolder.linearLayout, Double.valueOf(googlePlace.getRating()));
		}
		
		if(googlePlace.getVicinity() != null)
		{
			placeHolder.vicinity.setText(googlePlace.getVicinity());
		}	
		return row;
	}
	
	private static class PlaceHolder
	{
		TextView title;
		TextView distance;
		TextView vicinity;
		ImageView icon;
		LinearLayout linearLayout;
	}

}

