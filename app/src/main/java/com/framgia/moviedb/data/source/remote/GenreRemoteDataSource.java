package com.framgia.moviedb.data.source.remote;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.DataSource;
import com.framgia.moviedb.service.ServiceGenerator;
import com.framgia.moviedb.service.genre.ApiListGenre;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.remote
 */
public class GenreRemoteDataSource implements DataSource<Genre> {
    private static GenreRemoteDataSource sGenreRemoteDataSource;

    private GenreRemoteDataSource() {
    }

    public static GenreRemoteDataSource getInstance() {
        if (sGenreRemoteDataSource == null)
            return new GenreRemoteDataSource();
        return sGenreRemoteDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         final GetCallback getCallback) {
        ApiListGenre.ListGenres listGenres = ServiceGenerator.createService(
            ApiListGenre.ListGenres.class);
        Call<ApiListGenre.Response> call = listGenres.loadGenres(ApiListGenre.param());
        call.enqueue(new Callback<ApiListGenre.Response>() {
            @Override
            public void onResponse(Call<ApiListGenre.Response> call,
                                   Response<ApiListGenre.Response> response) {
                if (response != null) getCallback.onLoaded(response.body().getGenres());
                else getCallback.onNotAvailable();
            }

            @Override
            public void onFailure(Call<ApiListGenre.Response> call, Throwable t) {
                getCallback.onNotAvailable();
            }
        });
    }

    @Override
    public void saveData(@Nullable String type, Genre data) {
        // TODO: add data to model
    }
}
