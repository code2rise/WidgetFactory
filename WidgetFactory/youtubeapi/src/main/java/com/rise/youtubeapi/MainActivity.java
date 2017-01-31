package com.rise.youtubeapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGetVideos = null;
    private UIUpdaterHandler uiUpdaterHandler;

    public static final String CHANNEL_VIDEO_LIST_KEY = "ChannelVideoList";
    public static final int SUCCESS = 100;
    public static final int FAILURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiUpdaterHandler = new UIUpdaterHandler(this);
        btnGetVideos = (Button) findViewById(R.id.btn_get_videos);
        btnGetVideos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        new GetChannelVideosTask(uiUpdaterHandler).execute();
    }

    static class UIUpdaterHandler extends Handler {

        private WeakReference<MainActivity> activityWeakReference = null;

        public UIUpdaterHandler(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SUCCESS: {
                    Bundle bundle = msg.getData();
                    MainActivity activity = activityWeakReference.get();

                    if(bundle != null && activity != null) {
                        ArrayList<VideoData> channelVideosList = bundle.getParcelableArrayList(CHANNEL_VIDEO_LIST_KEY);
                        activity.showVideoListScreen(channelVideosList);
                    }

                    break;
                }
                case FAILURE: {
                    break;
                }
            }
        }
    }

    private void showVideoListScreen(ArrayList<VideoData> channelVideosList) {
        Intent videoListActivityIntent = new Intent(this, VideoListActivity.class);
        videoListActivityIntent.putParcelableArrayListExtra(MainActivity.CHANNEL_VIDEO_LIST_KEY, channelVideosList);
        startActivity(videoListActivityIntent);
    }
}
