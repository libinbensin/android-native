package com.android.foodbook.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.android.foodbook.MainApplication;
import com.android.foodbook.R;

import android.os.Build;

public class HttpHelper 
{
	private static final DefaultHttpClient DEFAULT_HTTP_CLIENT;
	
	private static HttpHelper instance = null;
	
	private HttpHelper() 
	{}
	
	public static HttpHelper getInstance()
	{
		if(instance == null)
		{
			instance = new HttpHelper();
		}
		return instance;
	}
	
	static
	{
		HttpParams httpParams = new BasicHttpParams();
		
		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpParams.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.UTF_8);
		
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpParams.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);
		
		httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE);
		httpParams.setParameter(CoreProtocolPNames.USER_AGENT, getUserAgent());
		
		
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", getTrustedSocketFactory(), 443));
		
		ThreadSafeClientConnManager conman = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		
		DEFAULT_HTTP_CLIENT = new DefaultHttpClient(conman, httpParams);
		
		
	}

	private static Object getUserAgent() 
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Apache-HttpClient/Bensin-Android/");
		builder.append("0.1");
		builder.append("; Android/" + Build.VERSION.RELEASE);
		builder.append("; " + Build.MANUFACTURER);
		builder.append("/" + Build.MODEL);
		
		return builder.toString();
	}
	
	
	public InputStream executeGet(String argUrl, final List<String> argString, Object argPostBody)
			throws IOException
	{
			HttpGet httpGet = new HttpGet(argUrl);
			
			return execute(httpGet);
	}
		
	
	public InputStream executePost(String argUrl, final List<String> argString, Object argPostBody)
		throws IOException
	{
		HttpPost httpPost = new HttpPost(argUrl);
		
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		return execute(httpPost);
	}
	
	
	private synchronized InputStream execute(final HttpUriRequest httpUriRequest)
		throws ClientProtocolException, IllegalStateException , IOException
	{
		HttpResponse httpResponse = DEFAULT_HTTP_CLIENT.execute(httpUriRequest);
		return httpResponse.getEntity().getContent();
	}
	
	private static KeyStore getServerTrustStore() throws Exception  
	{  
		//String keyStoreType = KeyStore.getDefaultType();
		
	    KeyStore trustStore = KeyStore.getInstance("BKS");
	    InputStream instream = MainApplication.getAppInstance().getResources().openRawResource(R.raw.bensin);  
	    try 
	    {  
	            trustStore.load(instream, "bensin".toCharArray());  
	    }  
	    finally 
	    {  
	            instream.close();  
	    }  
	    return trustStore;  
	}  
	
	public static SSLSocketFactory getTrustedSocketFactory()
	{
		SSLSocketFactory sslSocketFactory = null;
		
		try 
		{
			sslSocketFactory = new SSLSocketFactory(getServerTrustStore(), "bensin");
			
			X509HostnameVerifier x509HostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
			sslSocketFactory.setHostnameVerifier(x509HostnameVerifier);
		} 
		catch (KeyManagementException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnrecoverableKeyException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (KeyStoreException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sslSocketFactory;
	}
}
