package com.rise.youtubeapi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by RupeshCHAVAN on 30-01-2017.
 */
public class GetChannelVideosTask extends AsyncTask<String, Integer, Integer> {

    private int responseCodeHttp;
    private URL urlHttp;
    private HttpURLConnection connectionURLHttp;
    private StringBuilder responseStringBuilder;
    private BufferedReader bufferReader;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            urlHttp = new URL("https://www.googleapis.com/youtube/v3/search?key=" + Constants.YOUTUBE_API_KEY +
            "&channelId=" + Constants.CHANNEL_ID + "&part=snippet,id&type=video&order=date&maxResults=50");
            connectionURLHttp = (HttpURLConnection) urlHttp.openConnection();
            connectionURLHttp.setRequestMethod("GET");
            connectionURLHttp.setConnectTimeout(Constants.WS_TIMEOUT);
            connectionURLHttp.setReadTimeout(Constants.WS_READ_TIMEOUT);
            connectionURLHttp.connect();

            try {
                responseCodeHttp = connectionURLHttp.getResponseCode();
            } catch (IOException e) {
                responseCodeHttp = connectionURLHttp.getResponseCode();
            }

            if (responseCodeHttp == HttpURLConnection.HTTP_OK) {
                responseStringBuilder = new StringBuilder();
                InputStream isr = connectionURLHttp.getInputStream();
                bufferReader = new BufferedReader(new InputStreamReader(isr));
                String outputLine = "";
                while ((outputLine = bufferReader.readLine()) != null) {
                    responseStringBuilder.append(outputLine);
                }

                if(responseStringBuilder != null && !responseStringBuilder.toString().isEmpty()) {
                    Log.i("YouTubeAPI", responseStringBuilder.toString());
                }

                //Finally close the buffer reader
                if (bufferReader != null) {
                    bufferReader.close();
                }
            }
        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();

            responseCodeHttp = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connectionURLHttp != null) {
                connectionURLHttp.disconnect();
            }
        }

        return responseCodeHttp;
    }
}