package com.android.foodmark.loader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.Loader;

public abstract class BaseServiceLoader<D> extends Loader<LoaderResultOrException<D, Exception>>
{

	private static ExecutorService defaultExecutorService;
	
	private final Handler handler = new Handler();
	
	private final ExecutorService executor;
	
	private LoaderResultOrException<D, Exception> data;
	
	private Future<Void> future;
	
	public BaseServiceLoader(final Context context) 
	{
		this(context, null);
	}
	
	
	public BaseServiceLoader(final Context argContext , final ExecutorService argExecutor) 
	{
		super(argContext);
		
		if(argExecutor == null)
		{
			synchronized (BaseServiceLoader.class) 
			{
				if(defaultExecutorService == null)
				{
					defaultExecutorService = Executors.newFixedThreadPool(1);
				}
			}
			this.executor = defaultExecutorService;
		}
		else
		{
			this.executor = argExecutor;
		}
	}
	
	
	@Override
	protected void onStartLoading()
	{
		super.onStartLoading();
		
		if(data != null)
		{
			deliverResult(data);
		}
		else if(takeContentChanged() || future == null)
		{
			forceLoad();
		}
	}
	
	@Override
	protected void onForceLoad()
	{
		super.onForceLoad();
		
		try
		{
			if(future != null)
			{
				future.cancel(true);
			}
			future = executor.submit(new ExecuteCallable());
		}
		catch(final RejectedExecutionException exception)
		{
			deliverResult(new LoaderResultOrException<D, Exception>(exception));
		}
	}
	
	@Override
	protected void onStopLoading()
	{
		super.onStopLoading();
	}
	
	@Override
	protected void onAbandon()
	{
		super.onAbandon();
	}
	
	@Override
	protected void onReset()
	{
		super.onReset();
		if(future != null)
		{
			future.cancel(true);
		}
		data = null;
		future = null;
	}
	
	private void dispatchResult(final LoaderResultOrException<D, Exception> argData)
	{
		this.future = null;
		this.data = argData;
		
		if(isAbandoned())
		{
			// log it
		}
		else if(isStarted())
		{
			deliverResult(argData);
		}
		else
		{
			
		}
		
	}
	
	protected abstract LoaderResultOrException<D, Exception> runOnBackground();
	
	private class ExecuteCallable implements Callable<Void>
	{

		@Override
		public Void call() throws Exception 
		{
			LoaderResultOrException< D, Exception> d = null;
			
			try
			{
				d = runOnBackground();
			}
			catch(final Exception e)
			{
				d = new LoaderResultOrException<D, Exception>(e);
			}
			final LoaderResultOrException<D, Exception> loaderResultOrException = d;
			
			handler.post(new Runnable() 
			{	
				@Override
				public void run() 
				{
					if(loaderResultOrException != null)
					{
						dispatchResult(loaderResultOrException);
					}
				}
			});
			return null;
		}
		
	}
	
}
