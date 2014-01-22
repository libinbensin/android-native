package com.android.foodbook.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.foodbook.R;
import com.android.foodbook.model.PlaceDetail;

public class ReviewAdapter extends ArrayAdapter<PlaceDetail.Reviews>
{

	private Activity mContext;
	
	
	public ReviewAdapter(Activity context)
	{
		super(context, R.layout.place_review_list);
		mContext = context;
	}
	
	@Override
	public View getView(int position, View contentView , ViewGroup parent)
	{
		View row = contentView;
		ReviewHolder reviewHolder = null;
		
		if(row == null)
		{
			reviewHolder = new ReviewHolder();
			
			row = mContext.getLayoutInflater().inflate(R.layout.place_review_item, null);
			
			reviewHolder.authorName = (TextView) row.findViewById(R.id.review_author_name);
			reviewHolder.text = (TextView) row.findViewById(R.id.review_text);
			reviewHolder.authorUrl = (TextView) row.findViewById(R.id.review_author_url);
			reviewHolder.ratingBar = (RatingBar) row.findViewById(R.id.ratingBar);
			row.setTag(reviewHolder);
		}
		else
		{
			reviewHolder = (ReviewHolder) row.getTag();
		}
		
		PlaceDetail.Reviews review = getItem(position);
		
		if(review != null)
		{
			reviewHolder.text.setText(Html.fromHtml(review.getText()));
			reviewHolder.authorName.setText(review.getAuthorName());
			reviewHolder.authorUrl.setText(review.getAuthorUrl());
			reviewHolder.authorUrl.setMovementMethod(LinkMovementMethod.getInstance());
			reviewHolder.ratingBar.setRating(Float.valueOf(review.getRating()));
		}
		return row;
		
	}
	
	
	private static class ReviewHolder
	{
		TextView authorName;
		TextView text;
		TextView authorUrl;
		RatingBar ratingBar;
	}
}
