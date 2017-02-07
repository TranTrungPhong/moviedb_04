package com.framgia.moviedb.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.DataSource;
import com.framgia.moviedb.service.movie.ApiListMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.local
 */
public class MovieLocalDataSource implements DataSource<Movie> {
    private static MovieLocalDataSource sMovieLocalDataSource;
    private DataHelper mDataHelper;

    private MovieLocalDataSource(Context context) {
        mDataHelper = new DataHelper(context);
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (sMovieLocalDataSource == null)
            return new MovieLocalDataSource(context);
        return sMovieLocalDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        List<Movie> movies = null;
        SQLiteDatabase db = mDataHelper.getReadableDatabase();
        String[] projection = new String[]{
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_ENTRY_ID,
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_TITLE,
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_POSTER,
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_OVERVIEW,
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_RATE_AVG,
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_FAVORITE
        };
        String selection = MoviePersistenceContract.MovieEntry.COLUMN_NAME_TYPE + " = ?";
        String[] selectionArgs = {type};
        String sortOrder = ApiListMovie.TOP_RATED.equals(type) ?
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_RATE_AVG + " DESC" : null;
        Cursor cursor = db.query(
            MoviePersistenceContract.MovieEntry.TABLE_NAME,
            projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            movies = new ArrayList<>();
            while (cursor.moveToNext()) {
                movies.add(new Movie(cursor));
            }
        }
        if (cursor != null) cursor.close();
        if (movies == null) getCallback.onNotAvailable();
        else getCallback.onLoaded(movies);
    }

    @Override
    public void saveData(@Nullable String type, Movie data) {
        // TODO: save data to sqlite
    }
}
