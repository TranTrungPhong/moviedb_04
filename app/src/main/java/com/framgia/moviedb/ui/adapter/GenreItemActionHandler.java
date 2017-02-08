package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.feature.main.MainContract;

/**
 * Created by tuannt on 08/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class GenreItemActionHandler {
    private MainContract.Presenter mListener;

    public GenreItemActionHandler(MainContract.Presenter listener) {
        mListener = listener;
    }

    public void genreClicked(Genre genre) {
        mListener.openGenreDetails(genre);
    }
}
