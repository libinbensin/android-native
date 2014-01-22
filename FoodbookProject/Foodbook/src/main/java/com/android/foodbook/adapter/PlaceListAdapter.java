package com.android.foodbook.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.foodbook.MainApplication;
import com.android.foodbook.R;
import com.android.foodbook.constants.AppConstant;
import com.android.foodbook.model.PlaceList;
import com.android.foodbook.utils.AppUtil;
import com.android.foodbook.widgets.StarRatingWidget;

public class PlaceListAdapter extends ArrayAdapter<PlaceList>
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
		PlaceList placeList = getItem(position);
		
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
		if(placeList.getIcon() != null)
		{
			// set animation
			AppUtil.setImageAnimation(placeHolder.icon, 1000);
			MainApplication.getImageDownloader().download(placeList.getIcon(), placeHolder.icon);
		}
		
		if(placeList.getDescription() != null)
		{
			placeHolder.title.setText(placeList.getDescription());
		}
		if(placeList.getDistance() != null)
		{
			placeHolder.distance.setText(
                    mContext.getResources().getString(R.string.distance_title) +
                    AppConstant.SPACE +
                    placeList.getDistance() +
                    mContext.getResources().getString(R.string.distance_miles));
		}
		
		if(placeList.getRating() != null)
		{
			StarRatingWidget starRatingWidget = new StarRatingWidget(mContext);
			placeHolder.linearLayout.removeAllViews();
			starRatingWidget.setStarRating(
                    placeHolder.linearLayout, Double.valueOf(placeList.getRating()));
		}
		
		if(placeList.getVicinity() != null)
		{
			placeHolder.vicinity.setText(placeList.getVicinity());
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

