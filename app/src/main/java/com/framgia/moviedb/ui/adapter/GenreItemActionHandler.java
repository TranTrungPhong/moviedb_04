package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.feature.main.MainContract;
import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

/**
 * Created by tuannt on 08/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class GenreItemActionHandler<T> {
    private T mListener;

    public GenreItemActionHandler(T listener) {
        mListener = listener;
    }

    public void genreClicked(Genre genre) {
        if (mListener instanceof MainContract.Presenter)
            ((MainContract.Presenter) mListener).openGenreDetails(genre);
        else if (mListener instanceof MovieDetailContract.Presenter) {
            ((MovieDetailContract.Presenter) mListener).openGenreDetails(genre);
        }
    }
}
