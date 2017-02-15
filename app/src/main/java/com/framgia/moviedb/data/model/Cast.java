package com.framgia.moviedb.data.model;

import com.framgia.moviedb.service.ApiCore;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.model
 */
public class Cast implements Serializable {
    @SerializedName("cast")
    private List<Cast> mCasts;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_path")
    private String mProfile;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfile() {
        return mProfile;
    }

    public void setProfile(String profile) {
        this.mProfile = profile;
    }

    public List<Cast> getCasts() {
        return mCasts;
    }

    public void setCasts(List<Cast> casts) {
        mCasts = casts;
    }

    public String getFullProfilePath() {
        return ApiCore.BASE_IMAGE_URL + getProfile();
    }
}
