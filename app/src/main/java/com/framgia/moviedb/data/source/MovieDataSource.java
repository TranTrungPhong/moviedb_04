package com.framgia.moviedb.data.source;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public interface MovieDataSource extends DataSource<Movie> {
    interface LoadCallback<T> {
        void onLoaded(T data);
        void onNotAvailable();
    }
    void getMovies(@Nullable String type, @Nullable String page, GetCallback getCallback);
    void saveMovie(@Nullable String type, Movie movie);
    void deleteAllData(@Nullable String type);
    boolean getFavorite(Movie data);
    void updateFavorite(@Nullable String type, Movie data);
    void loadMovies(String type, @Nullable String idOrQuery, String page, GetCallback getCallback);
    void loadFavorite(GetCallback getCallback);
    void loadDetalMovie(String movieId, LoadCallback loadCallback);
    void loadMovieReview(String movieId, String page, GetCallback getCallback);
}
