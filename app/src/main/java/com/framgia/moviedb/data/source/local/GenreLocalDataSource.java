package com.framgia.moviedb.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.local
 */
public class GenreLocalDataSource implements DataSource<Genre> {
    private static GenreLocalDataSource sGenreLocalDataSource;
    private DataHelper mDataHelper;

    private GenreLocalDataSource(Context context) {
        mDataHelper = new DataHelper(context);
    }

    public static GenreLocalDataSource getInstance(Context context) {
        if (sGenreLocalDataSource == null)
            return new GenreLocalDataSource(context);
        return sGenreLocalDataSource;
    }

    @Override
    public void getDatas(@Nullable String type, @Nullable String page,
                         GetCallback getCallback) {
        List<Genre> genres = null;
        SQLiteDatabase db = mDataHelper.getReadableDatabase();
        String[] projection = new String[]{
            GenrePersistenceContract.GenreEntry.COLUMN_NAME_ENTRY_ID,
            GenrePersistenceContract.GenreEntry.COLUMN_NAME_TITLE
        };
        Cursor cursor = db.query(
            GenrePersistenceContract.GenreEntry.TABLE_NAME,
            projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            genres = new ArrayList<>();
            while (cursor.moveToNext()) {
                genres.add(new Genre(cursor));
            }
        }
        if (cursor != null) cursor.close();
        if (genres == null) getCallback.onNotAvailable();
        else getCallback.onLoaded(genres);
    }

    @Override
    public void saveData(@Nullable String type, Genre data) {
        // TODO: save data to sqlite
    }
}
