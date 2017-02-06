package com.framgia.moviedb.data.source.local;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.DataSource;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.local
 */
public class GenreLocalDataSource implements DataSource<Genre> {
    private static GenreLocalDataSource sGenreLocalDataSource;

    private GenreLocalDataSource() {
    }

    public static GenreLocalDataSource getInstance() {
        if (sGenreLocalDataSource == null)
            return new GenreLocalDataSource();
        return sGenreLocalDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        // TODO: get data from sqlite
    }

    @Override
    public void saveData(Genre data) {
        // TODO: save data to sqlite
    }
}
