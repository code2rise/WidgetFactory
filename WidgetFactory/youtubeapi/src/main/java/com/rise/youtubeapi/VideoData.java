package com.rise.youtubeapi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by RupeshCHAVAN on 31-01-2017.
 */

public class VideoData implements Parcelable {

    private String videoId;
    private Date publishedAt;
    private String channelId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelTitle;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoId);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
        dest.writeString(this.channelId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.channelTitle);
    }

    public VideoData() {
    }

    protected VideoData(Parcel in) {
        this.videoId = in.readString();
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
        this.channelId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.thumbnailUrl = in.readString();
        this.channelTitle = in.readString();
    }

    public static final Parcelable.Creator<VideoData> CREATOR = new Parcelable.Creator<VideoData>() {
        @Override
        public VideoData createFromParcel(Parcel source) {
            return new VideoData(source);
        }

        @Override
        public VideoData[] newArray(int size) {
            return new VideoData[size];
        }
    };
}

