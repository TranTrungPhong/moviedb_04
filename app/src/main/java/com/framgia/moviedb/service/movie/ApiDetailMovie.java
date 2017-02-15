package com.framgia.moviedb.service.movie;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.service.ApiCore;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class ApiDetailMovie {
    public interface MovieDetail {
        @GET("movie/{" + Param.MOVIE_ID + "}")
        Call<Movie> loadDetailMovies(@Path(Param.MOVIE_ID) String movieId, @QueryMap
            HashMap<String, String> options);
    }

    public class Param {
        static final String MOVIE_ID = "movie_id";
        static final String APPEND = "append_to_response";
    }

    public class Value {
        static final String APPEND = "videos,casts";
    }

    public static HashMap<String, String> param() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiCore.Param.API_KEY, ApiCore.Value.API_KEY);
        params.put(Param.APPEND, Value.APPEND);
        return params;
    }
}
