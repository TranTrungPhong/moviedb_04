package com.framgia.moviedb.data.model;

import android.database.Cursor;

import com.framgia.moviedb.data.source.local.GenrePersistenceContract;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.service.movie
 */
public class Genre {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public Genre(Cursor cursor) {
        mId = cursor.getInt(
            cursor.getColumnIndexOrThrow(GenrePersistenceContract.GenreEntry.COLUMN_NAME_ENTRY_ID));
        mName = cursor.getString(
            cursor.getColumnIndexOrThrow(GenrePersistenceContract.GenreEntry.COLUMN_NAME_TITLE));
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
