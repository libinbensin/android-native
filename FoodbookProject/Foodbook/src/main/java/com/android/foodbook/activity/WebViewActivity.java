package com.android.foodbook.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.foodbook.R;
import com.android.foodbook.constants.AppBundle;

/**
 * Created by libin on 12/31/13.
 */
public class WebViewActivity extends BaseActivity
{
    private WebView mWebView;
    private Context myApp = this;
    private boolean showing = false;
    private boolean loading = false;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_webview_layout);

        mWebView = (WebView) findViewById(R.id.webView_container);
        mWebView.setVisibility(View.INVISIBLE);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        String userAgent = webSettings.getUserAgentString();
        //webSettings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.45 Safari/535.19");
        webSettings.setUserAgentString("Mozilla/5.0 (X11;Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.0.0 Mobile Safari/537.36");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webSettings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        String url = getIntent().getStringExtra(AppBundle.WEB_URL);

        /*if(url.contains("plus.google.com"))
        {
            url = url.replace("plus.google.com","plus.google.com/app/basic/local");
        }*/
        if(url.contains("hl=en-US"))
        {
        url = url.replace("hl=en-US","review=1");
        }

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return (motionEvent.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        //url = "https://plus.google.com/_/widget/render/localreview?hl=en&origin=https%3A%2F%2Fplus.google.com&placeid=10473799314121550194";

        //url = "https://plus.google.com/_/widget/render/localreview?hl=en&origin=https%3A%2F%2Fplus.google.com&placeid=10473799314121550194&source=lo-pp&jsh=m%3B%2F_%2Fscs%2Fabc-static%2F_%2Fjs%2Fk%3Dgapi.gapi.en.5GL3tg_C6Nc.O%2Fm%3D__features__%2Frt%3Dj%2Fd%3D1%2Frs%3DAItRSTPfsfz-ifAZeiHMk7mKfiGQJuhVFw#rpctoken=378730872&_methods=onSuccess%2ConCancel%2ConError%2C_ready%2C_close%2C_open%2C_resizeMe%2C_renderstart&id=I2_1388700389953&parent=https%3A%2F%2Fplus.google.com&pfname="
        //url = "http://lexandera.com/files/jsexamples/alert.html";
        mWebView.addJavascriptInterface(new MyJavaScriptInterface(),"android");
        mWebView.setWebChromeClient(new AppWebChromeClient());
        mWebView.setWebViewClient(new AppWebClient());
        mWebView.loadUrl(url);
    }


    private class AppWebClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView , String url)
        {
            webView.loadUrl(url);
            showing = true;
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(showing && !url.contains("ServiceLogin"))
            {
                mWebView.setVisibility(View.INVISIBLE);
                showing = false;
            }
            if(!loading)
            {
                loading = true;
                showLoading();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            //view.loadUrl("javascript:android.onData(document.getElementById('root')[0].innerHTML);");
            if(!showing)
            {
                mWebView.loadUrl("javascript:android.onData(document.getElementById('glass-content').innerHTML);");
            }
            //mWebView.loadUrl("javascript:android.onData(document.getElementById('glass-content')[0].innerHTML);");
            //view.loadUrl("javascript:android.onData(document.getElementById('glass-content').style);");

            //mWebView.loadUrl("javascript:document.getElementById('contentPane').style.display = 'none'; ");
            //mWebView.loadUrl("javascript:document.getElementById('glass-content').style = '\"width: 915px; height: 616px;\";");
            //mWebView.loadUrl("javascript:document.getElementById('contentPane').style = 'visibility: hidden';");
            //hideLoading();
            //view.getSettings().setUseWideViewPort(true);
            //view.getSettings().setLoadWithOverviewMode(true);
            //mWebView.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementById('glass-content').value);");
            //mWebView.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            //view.loadUrl("javascript:(function() { " + " var varSendText = document.getElementById('sendtextcoupon').value; " + "})()" + "return" + "varSendText");
            //view.setVisibility(View.VISIBLE);
            //hideLoading();
        }
    }

    private class AppWebChromeClient extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url ,String message , JsResult result)
        {
            return true;
        }

        @Override
        public boolean onJsPrompt(
                WebView view, String url, String message, String defaultValue, JsPromptResult result)
        {
            String msg = message;
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url ,String message , JsResult result)
        {
           String msg = message;
           return true;
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result)
        {
            String msg = message;
            return true;
        }

        @Override
        public void onShowCustomView (View view, WebChromeClient.CustomViewCallback callback)
        {
            View customView = view;
        }

        @Override
        public void onProgressChanged(WebView view , int newProgress)
        {
            if(newProgress == 100 && showing)
            {
                mWebView.setVisibility(View.VISIBLE);
                hideLoading();
                loading = false;
            }
        }
    }

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        public void onData(String value)
        {
            if(!showing)
            {
                showing = true;
            }
        }

        @SuppressWarnings("unused")
        @JavascriptInterface
        public void showHTML(String html)
        {

            // check if the html text contains

            /*new AlertDialog.Builder(myApp)
                    .setTitle("HTML")
                    .setMessage(html)
                    .setPositiveButton(android.R.string.ok, null)
                    .setCancelable(false)
                    .create()
                    .show();*/
        }
    }
}
