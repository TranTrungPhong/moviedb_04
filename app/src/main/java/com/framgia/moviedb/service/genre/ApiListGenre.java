package com.framgia.moviedb.service.genre;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.service.ApiCore;
import com.framgia.moviedb.util.ApiUtil;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class ApiListGenre {

    public interface ListGenres {
        @GET("genre/movie/list")
        Call<Response> loadGenres(@QueryMap HashMap<String, String> options);
    }

    public class Response {
        @SerializedName("genres")
        private List<Genre> mGenres;

        public List<Genre> getGenres() {
            return mGenres;
        }

        public void setGenres(List<Genre> genres) {
            mGenres = genres;
        }
    }

    public static HashMap<String, String> param() {
        return ApiUtil.param();
    }
}
