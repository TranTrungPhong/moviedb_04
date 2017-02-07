package com.framgia.moviedb.feature.main;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.DataSource;
import com.framgia.moviedb.service.movie.ApiListMovie;

import java.util.List;

/**
 * Created by tuannt on 06/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.splash
 */
public class MainPresenter implements
    MainContract.Presenter {
    private MainContract.View mMainView;
    private DataSource mGenreRepository;
    private DataSource mMovieRepository;
    private int mFirstPage = 1;
    private int mState = 0;
    private static final int GET_DATA_DONE = 5;

    public MainPresenter(MainContract.View mainView,
                         DataSource genreRepository,
                         DataSource movieRepository) {
        mMainView = mainView;
        mGenreRepository = genreRepository;
        mMovieRepository = movieRepository;
    }

    @Override
    public void start() {
        mMainView.onPrepare();
        loadGenres();
        loadNowPlayingMovies(String.valueOf(mFirstPage));
        loadPopularMovies(String.valueOf(mFirstPage));
        loadTopRatedMovies(String.valueOf(mFirstPage));
        loadUpComingMovies(String.valueOf(mFirstPage));
    }

    @Override
    public void loadGenres() {
        mGenreRepository.getDatas(null, null,
            new DataSource.GetCallback<Genre>() {
                @Override
                public void onLoaded(List<Genre> datas) {
                    mMainView.onGenresLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    mMainView.onError();
                }
            });
    }

    @Override
    public void loadNowPlayingMovies(String page) {
        mMovieRepository.getDatas(ApiListMovie.NOW_PLAYING, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onNowPlayingMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    mMainView.onError();
                }
            });
    }

    @Override
    public void loadPopularMovies(String page) {
        mMovieRepository.getDatas(ApiListMovie.POPULAR, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onPopularMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    mMainView.onError();
                }
            });
    }

    @Override
    public void loadTopRatedMovies(String page) {
        mMovieRepository.getDatas(ApiListMovie.TOP_RATED, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onTopRatedMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    mMainView.onError();
                }
            });
    }

    @Override
    public void loadUpComingMovies(String page) {
        mMovieRepository.getDatas(ApiListMovie.UPCOMING, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onUpComingMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    mMainView.onError();
                }
            });
    }

    @Override
    public void checkState() {
        mState++;
        if (mState == GET_DATA_DONE) mMainView.onSuccess();
    }
}
