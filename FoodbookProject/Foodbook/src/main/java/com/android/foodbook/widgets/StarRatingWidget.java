package com.android.foodbook.widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StarRatingWidget
{  
	private Context mContext;
	
	public StarRatingWidget(final Context context) 
	{
		mContext = context;
	}
	
	public View setStarRating(View v ,Double rating)
	{
		LinearLayout layout = (LinearLayout) v;
		for(int index = 0 ; index < rating.intValue() ; index ++)
		{
			ImageView imageView = new ImageView(mContext);
			LayoutParams layoutParams = new LayoutParams(40, 40);
			imageView.setLayoutParams(layoutParams);
			imageView.setBackgroundResource(android.R.drawable.btn_star_big_on);
			layout.addView(imageView);
		}
		return v;
		
	}
}
