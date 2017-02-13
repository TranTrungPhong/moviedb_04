package com.framgia.moviedb.data.source;

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
}
