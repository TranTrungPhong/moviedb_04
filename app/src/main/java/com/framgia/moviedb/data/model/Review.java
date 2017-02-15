package com.framgia.moviedb.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuannt on 14/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.model
 */
public class Review {
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
