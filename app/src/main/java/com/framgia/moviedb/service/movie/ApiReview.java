package com.framgia.moviedb.service.movie;

import com.framgia.moviedb.data.model.Review;
import com.framgia.moviedb.util.ApiUtil;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class ApiReview {
    public interface ListReview {
        @GET("movie/{movie_id}/reviews")
        Call<Response> loadReviews(@Path("movie_id") String movieId, @QueryMap
            HashMap<String, String> options);
    }

    private class Param {
        static final String PAGE = "page";
    }

    public class Response {
        @SerializedName("results")
        private List<Review> mReviews;

        public List<Review> getReviews() {
            return mReviews;
        }

        public void setReviews(List<Review> reviews) {
            mReviews = reviews;
        }
    }

    public static HashMap<String, String> param(String page) {
        HashMap<String, String> params = ApiUtil.param();
        params.put(Param.PAGE, page);
        return params;
    }
}
