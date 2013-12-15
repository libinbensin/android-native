package com.android.foodmark.adapter;

import com.android.foodmark.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchCursorAdapter extends CursorAdapter
{

	public SearchCursorAdapter(Context context, Cursor c) 
	{
		super(context,c, 0);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		TextView textView = (TextView)view.findViewById(R.id.placeName);
		textView.setText(cursor.getString(1));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) 
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = null;
		
		view = inflater.inflate(R.layout.searchview_item, parent, false);
		return view;
	}

}
