package com.framgia.moviedb.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service
 */
public class ServiceGenerator {
    private static Retrofit.Builder sBuilder =
        new Retrofit.Builder()
            .baseUrl(ApiCore.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit sRetrofit = sBuilder.build();

    public static <T> T createService(Class<T> serviceClass) {
        return sRetrofit.create(serviceClass);
    }
}
