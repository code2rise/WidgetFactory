package com.rise.widgetfactory.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.customviews.CustomWebView;


public class WebViewActivity extends AppCompatActivity {

    private CustomWebView webView = null;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra("url");

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading page..");
        progressDialog.setCancelable(false);

        webView = (CustomWebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportMultipleWindows(true);

//        webView.initHandler(webviewResponseHandler);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                progressDialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                progressDialog.hide();
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {

                super.onLoadResource(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg)
            {
                Log.i("EntrataTablet", view.getOriginalUrl());

                WebView windowWebView = new WebView(WebViewActivity.this);
                {
                    windowWebView.getSettings().setJavaScriptEnabled(true);
                    windowWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    windowWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

                    windowWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                    windowWebView.setScrollbarFadingEnabled(false);
                    windowWebView.getSettings().setLoadWithOverviewMode(true);
                    windowWebView.getSettings().setSupportMultipleWindows(true);

                    windowWebView.setWebChromeClient(this);

                    windowWebView.setWebViewClient(new WebViewClient() {

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url)
                        {

                            if( url.contains("&action=download_document") )
                            {
                                Intent launchBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                                startActivity(launchBrowserIntent);
                            }

                            return super.shouldOverrideUrlLoading(view, url);
                        }
                    });
                }

                WebView.WebViewTransport transport = (WebView.WebViewTransport)resultMsg.obj;
                {
                    transport.setWebView(windowWebView);
                    resultMsg.sendToTarget();
                }

                return true;
            }

        });

        webView.loadUrl(url);
    }

    Handler webviewResponseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CustomWebView.PAGE_LOADED: {
                    progressDialog.hide();
                    break;
                }
                case CustomWebView.CONNECTION_TIMEOUT: {
                    progressDialog.hide();
                    break;
                }
            }

            super.handleMessage(msg);
        }
    };
}
