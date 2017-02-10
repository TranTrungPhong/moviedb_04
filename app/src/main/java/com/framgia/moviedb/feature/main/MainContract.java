package com.framgia.moviedb.feature.main;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.BaseView;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;

import java.util.List;

/**
 * Created by tuannt on 06/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.splash
 */
public interface MainContract {
    interface View extends BaseView {
        void onGenresLoaded(List<Genre> genres);
        void onNowPlayingMoviesLoaded(List<Movie> movies);
        void onPopularMoviesLoaded(List<Movie> movies);
        void onTopRatedMoviesLoaded(List<Movie> movies);
        void onUpComingMoviesLoaded(List<Movie> movies);
        void onPrepare();
        void onSuccess();
        void onError();
        void showGenreDetailsUi(Genre genre);
        void showMovieDetailsUi(Movie movie);
        void showListDetailsUi(String type);
        void onRefreshDone();
    }

    interface Presenter extends BasePresenter {
        void loadGenres();
        void loadNowPlayingMovies(String page);
        void loadPopularMovies(String page);
        void loadTopRatedMovies(String page);
        void loadUpComingMovies(String page);
        void checkState();
        void openGenreDetails(Genre genre);
        void openMovieDetails(Movie movie);
        void openListDetails(String type);
        void refresh();
    }
}
