package com.framgia.moviedb.service;

import com.framgia.moviedb.BuildConfig;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class ApiCore {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String KEY = BuildConfig.API_KEY;

    public class Param {
        public static final String API_KEY = "api_key";
    }

    public class Value {
        public static final String API_KEY = ApiCore.KEY;
    }
}
