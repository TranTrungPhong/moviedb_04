package com.framgia.moviedb.data.source;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.local.GenreLocalDataSource;
import com.framgia.moviedb.data.source.remote.GenreRemoteDataSource;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class GenreRepository implements DataSource<Genre> {
    private static GenreRepository sGenreRepository;
    private DataSource mGenreRemoteDataSource;
    private DataSource mGenreLocalDataSource;

    public static GenreRepository getInstance() {
        if (sGenreRepository == null)
            return new GenreRepository(GenreRemoteDataSource.getInstance(),
                GenreLocalDataSource.getInstance());
        return sGenreRepository;
    }

    private GenreRepository(GenreRemoteDataSource genreRemoteDataSource,
                            GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
        mGenreRemoteDataSource = genreRemoteDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        mGenreRemoteDataSource.getDatas(type, page, getCallback);
    }

    @Override
    public void saveData(Genre data) {
        // TODO: add data to model
    }
}
