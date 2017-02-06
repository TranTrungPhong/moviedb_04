package com.framgia.moviedb.data.source.local;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.DataSource;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.local
 */
public class MovieLocalDataSource implements DataSource<Movie> {
    private static MovieLocalDataSource sMovieLocalDataSource;

    private MovieLocalDataSource() {
    }

    public static MovieLocalDataSource getInstance() {
        if (sMovieLocalDataSource == null)
            return new MovieLocalDataSource();
        return sMovieLocalDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        // TODO: get data from sqlite
    }

    @Override
    public void saveData(Movie data) {
        // TODO: save data to sqlite
    }
}
