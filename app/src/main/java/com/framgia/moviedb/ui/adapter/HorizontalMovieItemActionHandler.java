package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.feature.main.MainContract;

/**
 * Created by tuannt on 10/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class HorizontalMovieItemActionHandler {
    private MainContract.Presenter mListener;

    public HorizontalMovieItemActionHandler(MainContract.Presenter listener) {
        mListener = listener;
    }

    public void movieClicked(Movie movie) {
        mListener.openMovieDetails(movie);
    }
}
