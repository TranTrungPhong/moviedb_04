package com.framgia.moviedb.data.model;

import android.database.Cursor;

import com.framgia.moviedb.data.source.local.DataHelper;
import com.framgia.moviedb.data.source.local.MoviePersistenceContract;
import com.framgia.moviedb.service.ApiCore;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("genres")
    private List<Genre> mGenres;
    @SerializedName("id")
    private long mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("poster_path")
    private String mPoster;
    @SerializedName("backdrop_path")
    private String mBackdrop;
    @SerializedName("belongs_to_collection")
    private Collection mCollection;
    @SerializedName("production_companies")
    private List<Company> mCompanies;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("vote_average")
    private String mVoteAverage;
    private boolean mIsFavorite;

    public Movie(Cursor cursor) {
        mId = cursor.getInt(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_ENTRY_ID));
        mTitle = cursor.getString(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_TITLE));
        mPoster = cursor.getString(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_POSTER));
        mOverview = cursor.getString(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_OVERVIEW));
        mVoteAverage = cursor.getString(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_RATE_AVG));
        int favorite = cursor.getInt(
            cursor.getColumnIndexOrThrow(MoviePersistenceContract.MovieEntry.COLUMN_NAME_FAVORITE));
        mIsFavorite = favorite == DataHelper.TRUE_VALUE;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        mBackdrop = backdrop;
    }

    public Collection getCollection() {
        return mCollection;
    }

    public void setCollection(Collection collection) {
        mCollection = collection;
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    public void setCompanies(List<Company> companies) {
        mCompanies = companies;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getFullPosterUrl() {
        return ApiCore.BASE_IMAGE_URL + getPoster();
    }
}
