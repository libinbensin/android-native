package com.android.foodmark.loader;

import java.util.concurrent.ExecutorService;

import android.content.Context;

public abstract class BaseLoader<D> extends BaseServiceLoader<D>
{

	public BaseLoader(Context context) 
	{
		super(context);
	}
	
	public BaseLoader(Context context , ExecutorService service)
	{
		super(context,service);
	}
	
	
	protected abstract String getRequest();
	
	
}
