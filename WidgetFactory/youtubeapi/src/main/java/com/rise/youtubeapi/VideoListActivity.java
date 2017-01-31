package com.rise.youtubeapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    private RecyclerView videoListRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        videoListRecyclerview = (RecyclerView) findViewById(R.id.youtube_channel_video_list_recyclerview);
        videoListRecyclerview.addItemDecoration(new DividerItemDecoration(this));
        videoListRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<VideoData> videoList = getIntent().getExtras().getParcelableArrayList(MainActivity.CHANNEL_VIDEO_LIST_KEY);
        videoListRecyclerview.setAdapter(new VideoListRecyclerviewAdapter(this, videoList));
    }
}
