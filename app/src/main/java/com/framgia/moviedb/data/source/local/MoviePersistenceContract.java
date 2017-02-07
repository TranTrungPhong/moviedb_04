package com.framgia.moviedb.data.source.local;

import android.provider.BaseColumns;

public final class MoviePersistenceContract {
    private MoviePersistenceContract() {
    }

    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_RATE_AVG = "rate_avg";
        public static final String COLUMN_NAME_FAVORITE = "favorite";
    }
}
