/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framgia.moviedb.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movies.db";
    public static final int TRUE_VALUE = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_GENRE_ENTRIES =
        "CREATE TABLE " + GenrePersistenceContract.GenreEntry.TABLE_NAME + " (" +
            GenrePersistenceContract.GenreEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
            GenrePersistenceContract.GenreEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            GenrePersistenceContract.GenreEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
            " )";
    private static final String SQL_CREATE_MOVIE_ENTRIES =
        "CREATE TABLE " + MoviePersistenceContract.MovieEntry.TABLE_NAME + " (" +
            MoviePersistenceContract.MovieEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_POSTER + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_RATE_AVG + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_FAVORITE + BOOLEAN_TYPE + COMMA_SEP +
            " UNIQUE(" + MoviePersistenceContract.MovieEntry.COLUMN_NAME_ENTRY_ID + COMMA_SEP +
            MoviePersistenceContract.MovieEntry.COLUMN_NAME_TYPE +
            "))";
    private static final String SQL_DROP_GENRE_ENTRIES =
        "DROP TABLE IF EXISTS " + GenrePersistenceContract.GenreEntry.TABLE_NAME;
    private static final String SQL_DROP_MOVIE_ENTRIES =
        "DROP TABLE IF EXISTS " + MoviePersistenceContract.MovieEntry.TABLE_NAME;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DROP_GENRE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DROP_MOVIE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
