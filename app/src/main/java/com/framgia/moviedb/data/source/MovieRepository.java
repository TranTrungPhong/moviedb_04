package com.framgia.moviedb.data.source;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.local.MovieLocalDataSource;
import com.framgia.moviedb.data.source.remote.MovieRemoteDataSource;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class MovieRepository implements DataSource<Movie> {
    private static MovieRepository sMovieRepository;
    private DataSource mMovieRemoteDataSource;
    private DataSource mMovieLocalDataSource;

    public static MovieRepository getInstance() {
        if (sMovieRepository == null)
            return new MovieRepository(MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance());
        return sMovieRepository;
    }

    private MovieRepository(MovieRemoteDataSource movieRemoteDataSource,
                            MovieLocalDataSource movieLocalDataSource) {
        mMovieLocalDataSource = movieLocalDataSource;
        mMovieRemoteDataSource = movieRemoteDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        mMovieRemoteDataSource.getDatas(type, page, getCallback);
    }

    @Override
    public void saveData(Movie data) {
        // TODO: add data to model
    }
}
