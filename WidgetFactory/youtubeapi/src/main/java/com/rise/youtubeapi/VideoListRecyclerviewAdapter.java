package com.rise.youtubeapi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RupeshCHAVAN on 31-01-2017.
 */

public class VideoListRecyclerviewAdapter extends RecyclerView.Adapter<VideoListRecyclerviewAdapter.CustomViewHolder> {

    private Context mContext;
    private ArrayList<VideoData> videoList;
    private LayoutInflater layoutInflater;

    public VideoListRecyclerviewAdapter(Context mContext, ArrayList<VideoData> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView tvVideoTitle;

        public CustomViewHolder(View itemView) {
            super(itemView);

            tvVideoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            rootView = itemView;
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.video_list_item_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final VideoData videoData = videoList.get(position);
        holder.tvVideoTitle.setText(videoData.getTitle());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, videoData.getVideoId(), Toast.LENGTH_SHORT).show();
                Intent playVideoActivityIntent = new Intent(mContext, PlayVideoActivity.class);
                playVideoActivityIntent.putExtra(PlayVideoActivity.VIDEO_ID, videoData.getVideoId());
                mContext.startActivity(playVideoActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
