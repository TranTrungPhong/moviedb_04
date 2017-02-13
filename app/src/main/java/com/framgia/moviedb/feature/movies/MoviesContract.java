package com.framgia.moviedb.feature.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.BaseView;
import com.framgia.moviedb.data.model.Movie;

import java.util.List;

/**
 * Created by tuannt on 12/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.movies
 */
public interface MoviesContract {
    interface View extends BaseView {
        void onPrepare();
        void onMoviesLoaded(List<Movie> movies);
        void onMoviesNotAvailable();
        void onMoviesEmpty();
        void showMovieDetailsUi(int position, Movie movie);
        void removeItemFavorite(int position, Movie movie);
    }

    interface Presenter extends BasePresenter {
        void loadMovies(@NonNull String type, @Nullable String id);
        void updateFavorite(@Nullable String type, Movie movie);
        void openMovieDetails(int position, Movie movie);
        void removeItemFavorite(int position, Movie movie);
    }
}
