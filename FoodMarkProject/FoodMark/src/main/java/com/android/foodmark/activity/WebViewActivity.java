package com.android.foodmark.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppBundle;

/**
 * Created by libin on 12/31/13.
 */
public class WebViewActivity extends BaseActivity
{
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_webview_layout);

        mWebView = (WebView) findViewById(R.id.webView_container);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString("Android");
        String url = getIntent().getStringExtra(AppBundle.WEB_URL);
        mWebView.setWebViewClient(new AppWebClient());
        mWebView.loadUrl(url);
    }


    private class AppWebClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView , String url)
        {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            showLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            hideLoading();
        }
    }

}
