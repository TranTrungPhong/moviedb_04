package com.framgia.moviedb.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.model
 */
public class Video {
    @SerializedName("results")
    private List<Video> mVideos;
    private String mAvatar;
    @SerializedName("name")
    private String mName;
    @SerializedName("key")
    private String mKey;

    public List<Video> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
