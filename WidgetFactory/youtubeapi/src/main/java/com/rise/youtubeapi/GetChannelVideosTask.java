package com.rise.youtubeapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by RupeshCHAVAN on 30-01-2017.
 */
public class GetChannelVideosTask extends AsyncTask<String, Integer, Integer> {

    private int responseCodeHttp;
    private URL urlHttp;
    private HttpURLConnection connectionURLHttp;
    private StringBuilder responseStringBuilder;
    private BufferedReader bufferReader;
    private ArrayList<VideoData> channelVideosList = new ArrayList<>(50);

    private Handler uiUpdaterHandler;

    public GetChannelVideosTask(Handler uiUpdaterHandler) {
        this.uiUpdaterHandler = uiUpdaterHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                    try {
                        JSONObject videoListJson = new JSONObject(responseStringBuilder.toString());
                        String nextPageToken = videoListJson.optString("nextPageToken", null);
                        JSONArray itemsArray = videoListJson.optJSONArray("items");
                        for(int index=0; index<itemsArray.length(); index++) {
                            JSONObject itemJson = itemsArray.getJSONObject(index);
                            String videoId = itemJson.optJSONObject("id").optString("videoId");

                            JSONObject snippetJson = itemJson.optJSONObject("snippet");
                            String publishedAt = snippetJson.optString("publishedAt");
                            publishedAt = publishedAt.replaceAll("Z$", "+0000");
                            String channelId = snippetJson.optString("channelId");
                            String title = snippetJson.optString("title");
                            String description = snippetJson.optString("description");
                            String thumbnailUrl = snippetJson.optJSONObject("thumbnails").optJSONObject("default").optString("url");
                            String channelTitle = snippetJson.optString("channelTitle");

                            VideoData videoData = new VideoData();
                            videoData.setVideoId(videoId);
                            videoData.setDescription(description);
                            videoData.setChannelId(channelId);
                            videoData.setChannelTitle(channelTitle);
                            videoData.setThumbnailUrl(thumbnailUrl);
                            videoData.setTitle(title);

                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                            videoData.setPublishedAt(dateFormatter.parse(publishedAt));

                            channelVideosList.add(videoData);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);

        Message msg = Message.obtain();
        msg.what = MainActivity.SUCCESS;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MainActivity.CHANNEL_VIDEO_LIST_KEY, channelVideosList);
        msg.setData(bundle);
        uiUpdaterHandler.sendMessage(msg);
    }
}