package com.framgia.moviedb.feature.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieDataSource;
import com.framgia.moviedb.data.source.MovieRepository;

import java.util.List;

/**
 * Created by tuannt on 12/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.movies
 */
public class MoviesPresenter implements MoviesContract.Presenter {
    private MoviesContract.View mMoviesView;
    private MovieDataSource mMovieRepository;
    private int mPage = Integer.parseInt(MovieRepository.FIRST_PAGE);

    public MoviesPresenter(MoviesContract.View moviesView, MovieDataSource movieRepository) {
        mMoviesView = moviesView;
        mMovieRepository = movieRepository;
    }

    @Override
    public void start() {
        mMoviesView.onPrepare();
    }

    @Override
    public void loadMovies(@NonNull String type, @Nullable String id) {
        mMovieRepository.loadMovies(type, id, String.valueOf(mPage),
            new MovieDataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mPage++;
                    mMoviesView.onMoviesLoaded(datas);
                }

                @Override
                public void onNotAvailable() {
                    mMoviesView.onMoviesNotAvailable();
                }
            });
    }

    @Override
    public void updateFavorite(@Nullable String type, Movie movie) {
        mMovieRepository.updateFavorite(type, movie);
    }

    @Override
    public void openMovieDetails(int position, Movie movie) {
        mMoviesView.showMovieDetailsUi(position, movie);
    }

    @Override
    public void removeItemFavorite(int position, Movie movie) {
        mMoviesView.removeItemFavorite(position, movie);
    }
}
