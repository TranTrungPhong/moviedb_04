package com.framgia.moviedb.data.source;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by tuannt on 06/02/2017.
 * Project: MyMovieDb
 * Package: com.framgia.moviedb.data.source
 */
public interface DataSource<T> {
    interface GetCallback<T> {
        void onLoaded(List<T> datas);
        void onNotAvailable();
    }
    void getDatas(@Nullable String type, @Nullable String page, GetCallback getCallback);
    void saveData(@Nullable String type, T data);
}
