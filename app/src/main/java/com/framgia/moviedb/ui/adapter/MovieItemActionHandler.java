package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.feature.main.MainContract;

/**
 * Created by tuannt on 10/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class MovieItemActionHandler {
    private MainContract.Presenter mListener;

    public MovieItemActionHandler(MainContract.Presenter listener) {
        mListener = listener;
    }

    public void listMovieClicked(String type) {
        mListener.openListDetails(type);
    }

    public void movieClicked(Movie movie) {
        mListener.openMovieDetails(movie);
    }

    public void updateFavorite(Movie movie) {
        movie.setFavorite(!movie.isFavorite());
    }
}
