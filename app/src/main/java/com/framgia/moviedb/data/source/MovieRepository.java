package com.framgia.moviedb.data.source;

import android.content.Context;
import android.support.annotation.Nullable;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.local.MovieLocalDataSource;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;
import com.framgia.moviedb.service.movie.ApiListMovie;

import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class MovieRepository implements MovieDataSource {
    private static MovieRepository sMovieRepository;
    private MovieDataSource mMovieRemoteDataSource;
    private MovieDataSource mMovieLocalDataSource;
    public final static String LOCAL_PAGE = "1";
    public final static String REFRESH_PAGE = "0";
    public final static String FIRST_PAGE = "1";

    public static MovieRepository getInstance(Context context) {
        if (sMovieRepository == null)
            return new MovieRepository(MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance(context));
        return sMovieRepository;
    }

    private MovieRepository(MovieRemoteDataSource movieRemoteDataSource,
                            MovieLocalDataSource movieLocalDataSource) {
        mMovieLocalDataSource = movieLocalDataSource;
        mMovieRemoteDataSource = movieRemoteDataSource;
    }

    @Override
    public void getMovies(@Nullable final String type, @Nullable final String page,
                          final GetCallback getCallback) {
        switch (page) {
            case REFRESH_PAGE:
                getDataFromRemote(type, FIRST_PAGE, getCallback);
                break;
            case LOCAL_PAGE:
                mMovieLocalDataSource.getMovies(type, page, new GetCallback<Movie>() {
                    @Override
                    public void onLoaded(List<Movie> datas) {
                        getCallback.onLoaded(datas);
                    }

                    @Override
                    public void onNotAvailable() {
                        getDataFromRemote(type, page, getCallback);
                    }
                });
                break;
            default:
                getDataFromRemote(type, page, getCallback);
                break;
        }
    }

    @Override
    public void saveMovie(@Nullable String type, Movie data) {
        mMovieLocalDataSource.saveMovie(type, data);
    }

    @Override
    public void deleteAllData(@Nullable String type) {
        mMovieLocalDataSource.deleteAllData(type);
    }

    @Override
    public boolean getFavorite(Movie data) {
        return mMovieLocalDataSource.getFavorite(data);
    }

    @Override
    public void updateFavorite(@Nullable String type, Movie data) {
        mMovieLocalDataSource.updateFavorite(type, data);
    }

    @Override
    public void loadMovies(String type, @Nullable String idOrQuery, String page,
                           final GetCallback getCallback) {
        GetCallback<Movie> callback = new GetCallback<Movie>() {
            @Override
            public void onLoaded(List<Movie> datas) {
                for (Movie movie : datas) {
                    movie.setFavorite(getFavorite(movie));
                }
                getCallback.onLoaded(datas);
            }

            @Override
            public void onNotAvailable() {
                getCallback.onNotAvailable();
            }
        };
        switch (type) {
            case Constant.IntentKey.EXTRA_NOW_PLAYING_MOVIES:
                getMovies(ApiListMovie.NOW_PLAYING, page, getCallback);
                break;
            case Constant.IntentKey.EXTRA_POPULAR_MOVIES:
                getMovies(ApiListMovie.POPULAR, page, getCallback);
                break;
            case Constant.IntentKey.EXTRA_TOP_RATED_MOVIES:
                getMovies(ApiListMovie.TOP_RATED, page, getCallback);
                break;
            case Constant.IntentKey.EXTRA_UPCOMING_MOVIES:
                getMovies(ApiListMovie.UPCOMING, page, getCallback);
                break;
            case Constant.IntentKey.EXTRA_SEARCH:
                mMovieRemoteDataSource.loadMovies(type, idOrQuery, page, callback);
                break;
            case Constant.IntentKey.EXTRA_GENRES:
                mMovieRemoteDataSource.loadMovies(type, idOrQuery, page, callback);
                break;
            case Constant.IntentKey.EXTRA_FAVORITE:
                loadFavorite(getCallback);
                break;
            case Constant.IntentKey.EXTRA_COMPANY:
                mMovieRemoteDataSource.loadMovies(type, idOrQuery, page, callback);
            default:
                break;
        }
    }

    @Override
    public void loadFavorite(GetCallback getCallback) {
        mMovieLocalDataSource.loadFavorite(getCallback);
    }

    private void getDataFromRemote(@Nullable final String type, @Nullable final String page,
                                   final GetCallback getCallback) {
        mMovieRemoteDataSource.getMovies(type, page, new GetCallback<Movie>() {
            @Override
            public void onLoaded(List<Movie> datas) {
                for (Movie movie : datas) {
                    movie.setType(type);
                    movie.setFavorite(getFavorite(movie));
                    if (FIRST_PAGE.equals(page)) saveMovie(type, movie);
                }
                getCallback.onLoaded(datas);
            }

            @Override
            public void onNotAvailable() {
                getCallback.onNotAvailable();
            }
        });
    }
}
