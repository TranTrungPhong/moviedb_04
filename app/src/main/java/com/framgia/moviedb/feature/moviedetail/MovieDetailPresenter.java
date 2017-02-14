package com.framgia.moviedb.feature.moviedetail;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieDataSource;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.moviedetail
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter {
    private MovieDetailContract.View mMovieDetailView;
    private MovieDataSource mMovieRepository;

    public MovieDetailPresenter(MovieDetailContract.View movieDetailView,
                                MovieDataSource movieRepository) {
        mMovieDetailView = movieDetailView;
        mMovieRepository = movieRepository;
    }

    @Override
    public void start() {
        mMovieDetailView.onPrepare();
    }

    @Override
    public void loadDetail(String movieId) {
        // TODO: load detail 
    }

    @Override
    public void openCompanyDetails(Company company) {
        mMovieDetailView.showCompanyDetailUi(company);
    }

    @Override
    public void updateFavorite(@Nullable String type, Movie movie) {
        movie.setFavorite(!movie.isFavorite());
        mMovieRepository.updateFavorite(type, movie);
    }

    @Override
    public void openVideoDetail(String key) {
        mMovieDetailView.showVideoDetailUi(key);
    }

    @Override
    public void shareLink(String title, String key) {
        mMovieDetailView.shareLink(title, key);
    }

    @Override
    public void openMovieReview(String title, String movieId) {
        mMovieDetailView.showMovieReviewUi(title, movieId);
    }
}
