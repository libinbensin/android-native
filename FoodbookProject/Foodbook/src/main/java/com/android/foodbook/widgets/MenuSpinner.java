package com.android.foodbook.widgets;

import android.content.Context;
import android.widget.Spinner;

public class MenuSpinner extends Spinner
{

	public MenuSpinner(Context context) 
	{
		super(context);

		LayoutParams layoutParams = new LayoutParams(500 ,LayoutParams.WRAP_CONTENT);
		
		setLayoutParams(layoutParams);
	}

}
