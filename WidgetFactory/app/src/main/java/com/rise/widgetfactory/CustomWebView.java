package com.rise.widgetfactory;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class CustomWebView extends WebView {
    ConnectToURL connectToURL = new ConnectToURL();
    String htmlContent = "";
    Context mContext;
    Handler webViewResponseHandler;

    private final static int OK = 200;
    public final static int CONNECTION_TIMEOUT = -2;
    private static final int TIMEOUT = 30000;
    public static final int PAGE_LOADED = 1;

    public CustomWebView(Context context) {
        super(context);
        mContext = context;
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initHandler(Handler webViewResponseHandler) {
        this.webViewResponseHandler = webViewResponseHandler;
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);

        if (connectToURL.getStatus() == AsyncTask.Status.FINISHED) {
            connectToURL = new ConnectToURL();
        } else {
            if (!(connectToURL.getStatus() == AsyncTask.Status.RUNNING)) {
                connectToURL.execute(this, url);
            }
        }
    }

    class ConnectToURL extends AsyncTask<Object, Void, Void> {
        WebView view = null;
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Object... urls) {
            // here you will use the url to access the headers.
            // in this case, the Content-Length one
            view = (WebView) urls[0];
            String connectionUrl = urls[1].toString();


            try {
                url = new URL(connectionUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setAllowUserInteraction(false);
                urlConnection.setInstanceFollowRedirects(true);
                urlConnection.setConnectTimeout(TIMEOUT);
                urlConnection.setReadTimeout(TIMEOUT);

                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                    readStream(in);

//                    Message msg = Message.obtain();
//                    msg.what = PAGE_LOADED;
//                    webViewResponseHandler.sendMessage(msg);
                }
                else {
                    in = new BufferedInputStream(urlConnection.getErrorStream());
                    readStream(in);

//                    Message msg = Message.obtain();
//                    msg.what = PAGE_LOADED;
//                    webViewResponseHandler.sendMessage(msg);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (SocketTimeoutException e) {
                Message msg = Message.obtain();
                msg.what = CONNECTION_TIMEOUT;
                msg.obj = mContext.getResources().getString(R.string.connection_timed_out);

                webViewResponseHandler.sendMessage(msg);

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }

            return null;
        }

        public void readStream(InputStream stream) {

            if (url != null && !url.getHost().equals(urlConnection.getURL().getHost())) {
                Toast.makeText(mContext, "Launch browser to do network Sign On.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

