package com.framgia.moviedb.data.source.remote;

import android.support.annotation.Nullable;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieDataSource;
import com.framgia.moviedb.service.ServiceGenerator;
import com.framgia.moviedb.service.movie.ApiListMovie;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.remote
 */
public class MovieRemoteDataSource implements MovieDataSource {
    private static MovieRemoteDataSource sMovieRemoteDataSource;

    private MovieRemoteDataSource() {
    }

    public static MovieRemoteDataSource getInstance() {
        if (sMovieRemoteDataSource == null)
            return new MovieRemoteDataSource();
        return sMovieRemoteDataSource;
    }

    @Override
    public void getMovies(@Nullable String type, @Nullable String page,
                          final GetCallback getCallback) {
        ApiListMovie.ListMovies listMovies = ServiceGenerator
            .createService(ApiListMovie.ListMovies.class);
        Call<ApiListMovie.Response> call = listMovies.loadMovies(
            type, ApiListMovie.param(page));
        call.enqueue(new Callback<ApiListMovie.Response>() {
            @Override
            public void onResponse(Call<ApiListMovie.Response> call,
                                   Response<ApiListMovie.Response> response) {
                if (response != null) {
                    getCallback.onLoaded(response.body().getMovies());
                } else getCallback.onNotAvailable();
            }

            @Override
            public void onFailure(Call<ApiListMovie.Response> call, Throwable t) {
                getCallback.onNotAvailable();
            }
        });
    }

    @Override
    public void saveMovie(@Nullable String type, Movie data) {
        // not require for remote
    }

    @Override
    public void deleteAllData(@Nullable String type) {
        // not require for remote
    }

    @Override
    public boolean getFavorite(Movie data) {
        return false;
    }

    @Override
    public void updateFavorite(@Nullable String type, Movie data) {
        // not require for remote
    }

    @Override
    public void loadMovies(String type, @Nullable String idOrQuery, String page,
                           final GetCallback getCallback) {
        Callback<ApiListMovie.Response> callback = new Callback<ApiListMovie.Response>() {
            @Override
            public void onResponse(Call<ApiListMovie.Response> call,
                                   Response<ApiListMovie.Response> response) {
                if (response != null) {
                    getCallback.onLoaded(response.body().getMovies());
                } else getCallback.onNotAvailable();
            }

            @Override
            public void onFailure(Call<ApiListMovie.Response> call, Throwable t) {
                getCallback.onNotAvailable();
            }
        };
        switch (type) {
            case Constant.IntentKey.EXTRA_SEARCH:
                ApiListMovie.SearchMovies searchMovies = ServiceGenerator
                    .createService(ApiListMovie.SearchMovies.class);
                HashMap<String, String> param = ApiListMovie.param(page);
                param.put(ApiListMovie.Param.QUERY, idOrQuery);
                Call<ApiListMovie.Response> searchCall = searchMovies.loadMovies(param);
                searchCall.enqueue(callback);
                break;
            case Constant.IntentKey.EXTRA_GENRES:
                ApiListMovie.GenreMovies genreMovies = ServiceGenerator
                    .createService(ApiListMovie.GenreMovies.class);
                Call<ApiListMovie.Response> genreCall = genreMovies.loadMovies(
                    idOrQuery, ApiListMovie.param(page));
                genreCall.enqueue(callback);
                break;
            case Constant.IntentKey.EXTRA_COMPANY:
                ApiListMovie.CompanyMovies companyMovies = ServiceGenerator
                    .createService(ApiListMovie.CompanyMovies.class);
                Call<ApiListMovie.Response> companyCall = companyMovies.loadMovies(
                    idOrQuery, ApiListMovie.param(page));
                companyCall.enqueue(callback);
                break;
            default:
                break;
        }
    }

    @Override
    public void loadFavorite(GetCallback getCallback) {
        // load favorite movies in list screen
    }
}
