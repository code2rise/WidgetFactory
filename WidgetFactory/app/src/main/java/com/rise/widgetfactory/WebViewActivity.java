package com.rise.widgetfactory;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;


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
