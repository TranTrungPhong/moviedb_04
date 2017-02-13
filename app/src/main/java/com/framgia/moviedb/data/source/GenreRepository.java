package com.framgia.moviedb.data.source;

import android.content.Context;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.local.GenreLocalDataSource;
import com.framgia.moviedb.data.source.remote.GenreRemoteDataSource;

import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class GenreRepository implements GenreDataSource {
    private static GenreRepository sGenreRepository;
    private GenreDataSource mGenreRemoteDataSource;
    private GenreDataSource mGenreLocalDataSource;

    public static GenreRepository getInstance(Context context) {
        if (sGenreRepository == null)
            return new GenreRepository(GenreRemoteDataSource.getInstance(),
                GenreLocalDataSource.getInstance(context));
        return sGenreRepository;
    }

    private GenreRepository(GenreRemoteDataSource genreRemoteDataSource,
                            GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
        mGenreRemoteDataSource = genreRemoteDataSource;
    }

    @Override
    public void getGenres(final boolean isRefresh, final GetCallback getCallback) {
        if (!isRefresh)
            mGenreLocalDataSource.getGenres(isRefresh, new GetCallback<Genre>() {
                @Override
                public void onLoaded(List<Genre> datas) {
                    getCallback.onLoaded(datas);
                }

                @Override
                public void onNotAvailable() {
                    getDataFromRemote(isRefresh, getCallback);
                }
            });
        else getDataFromRemote(isRefresh, getCallback);
    }

    @Override
    public void saveGenre(Genre data) {
        mGenreLocalDataSource.saveGenre(data);
    }

    @Override
    public void deleteAllGenres() {
        mGenreLocalDataSource.deleteAllGenres();
    }

    private void getDataFromRemote(boolean isRefresh, final GetCallback getCallback) {
        mGenreRemoteDataSource.getGenres(isRefresh, new GetCallback<Genre>() {
            @Override
            public void onLoaded(List<Genre> datas) {
                getCallback.onLoaded(datas);
                deleteAllGenres();
                for (Genre genre : datas) saveGenre(genre);
            }

            @Override
            public void onNotAvailable() {
                getCallback.onNotAvailable();
            }
        });
    }
}
