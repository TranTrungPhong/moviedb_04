package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.feature.movies.MoviesContract;

/**
 * Created by tuannt on 12/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class VerticalMovieItemActionHandler {
    private MoviesContract.Presenter mListener;

    public VerticalMovieItemActionHandler(MoviesContract.Presenter listener) {
        mListener = listener;
    }

    public void movieClicked(int position, Movie movie) {
        mListener.openMovieDetails(position, movie);
    }

    public void updateFavorite(int position, Movie movie) {
        movie.setFavorite(!movie.isFavorite());
        mListener.updateFavorite(movie.getType(), movie);
        mListener.removeItemFavorite(position, movie);
    }
}
