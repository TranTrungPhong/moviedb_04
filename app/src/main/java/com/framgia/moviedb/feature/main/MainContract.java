package com.framgia.moviedb.feature.main;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;

import java.util.List;

/**
 * Created by tuannt on 06/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.splash
 */
public interface MainContract {
    interface View {
        void onGenresLoaded(List<Genre> genres);
        void onNowPlayingMoviesLoaded(List<Movie> movies);
        void onPopularMoviesLoaded(List<Movie> movies);
        void onTopRatedMoviesLoaded(List<Movie> movies);
        void onUpComingMoviesLoaded(List<Movie> movies);
        void onPrepare();
        void onSuccess();
        void onError();
        void showGenreDetailsUi(Genre genre);
    }

    interface Presenter extends BasePresenter {
        void loadGenres();
        void loadNowPlayingMovies(String page);
        void loadPopularMovies(String page);
        void loadTopRatedMovies(String page);
        void loadUpComingMovies(String page);
        void checkState();
        void openGenreDetails(Genre genre);
    }
}
