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
        mMovieLocalDataSource.getDatas(type, page, new GetCallback<Movie>() {
            @Override
            public void onLoaded(List<Movie> datas) {
                getCallback.onLoaded(datas);
            }

            @Override
            public void onNotAvailable() {
                mMovieRemoteDataSource.getDatas(type, page, new GetCallback<Movie>() {
                    @Override
                    public void onLoaded(List<Movie> datas) {
                        getCallback.onLoaded(datas);
                    }

                    @Override
                    public void onNotAvailable() {
                        getCallback.onNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveData(@Nullable String type, Movie data) {
        // TODO: add data to model
    }
}
