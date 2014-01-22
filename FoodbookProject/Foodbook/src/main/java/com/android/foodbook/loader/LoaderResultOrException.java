package com.android.foodbook.loader;

public class LoaderResultOrException<T, E extends Exception> 
{
	private final boolean hasResult;
	private final T result;
	private final E exception;
	
	public LoaderResultOrException(final T argResult)
	{
		this.hasResult = true;
		this.result = argResult;
		this.exception = null;
	}
	
	public LoaderResultOrException(final E argException)
	{
		this.hasResult = false;
		this.result = null;
		this.exception = argException;
	}
	
	public boolean hasResult()
	{
		return hasResult;
	}
	
	public T getResult()
	{
		return result;
	}
	
	public E getException()
	{
		return exception;
	}
	
	@Override
	public String toString()
	{
		return 
		"{" +
				"hasResult = " + hasResult +
				" result = " + result +
				" exception = " + exception +
		"}";
	}
}
