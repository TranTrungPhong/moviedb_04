package com.framgia.moviedb.data.source;

import android.content.Context;
import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.local.MovieLocalDataSource;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;

import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class MovieRepository implements DataSource<Movie> {
    private static MovieRepository sMovieRepository;
    private DataSource mMovieRemoteDataSource;
    private DataSource mMovieLocalDataSource;
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
    public void getDatas(@Nullable final String type, @Nullable final String page,
                         final GetCallback getCallback) {
        switch (page) {
            case REFRESH_PAGE:
                getDataFromRemote(type, FIRST_PAGE, getCallback);
                break;
            case LOCAL_PAGE:
                mMovieLocalDataSource.getDatas(type, page, new GetCallback<Movie>() {
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
    public void saveData(@Nullable String type, Movie data) {
        mMovieLocalDataSource.saveData(type, data);
    }

    @Override
    public void deleteAllData(@Nullable String type) {
        mMovieLocalDataSource.deleteAllData(type);
    }

    @Override
    public boolean getFavorite(Movie data) {
        return mMovieLocalDataSource.getFavorite(data);
    }

    private void getDataFromRemote(@Nullable final String type, @Nullable final String page,
                                   final GetCallback getCallback) {
        mMovieRemoteDataSource.getDatas(type, page, new GetCallback<Movie>() {
            @Override
            public void onLoaded(List<Movie> datas) {
                for (Movie movie : datas) {
                    movie.setFavorite(getFavorite(movie));
                    if (FIRST_PAGE.equals(page)) saveData(type, movie);
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
