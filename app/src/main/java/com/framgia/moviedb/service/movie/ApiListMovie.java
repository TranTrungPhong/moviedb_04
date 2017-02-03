package com.framgia.moviedb.service.movie;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.service.ApiCore;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class ApiListMovie {
    private static final String TYPE_LIST = "type";
    public static final String NOW_PLAYING = "now_playing";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String UPCOMING = "upcoming";

    public interface ListMovies {
        @GET("movie/{" + TYPE_LIST + "}")
        Call<Response> loadMovies(@Path(TYPE_LIST) String type,
                                  @QueryMap HashMap<String, String> options);
    }

    private class Param {
        static final String PAGE = "page";
    }

    public class Response {
        @SerializedName("page")
        private String mPage;
        @SerializedName("results")
        private List<Movie> mMovies;

        public List<Movie> getMovies() {
            return mMovies;
        }

        public void setMovies(List<Movie> movies) {
            mMovies = movies;
        }

        public String getPage() {
            return mPage;
        }

        public void setPage(String page) {
            mPage = page;
        }
    }

    public static HashMap<String, String> param(String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiCore.Param.API_KEY, ApiCore.Value.API_KEY);
        params.put(Param.PAGE, page);
        return params;
    }
}
