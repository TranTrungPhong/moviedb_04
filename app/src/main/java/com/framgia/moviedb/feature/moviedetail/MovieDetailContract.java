package com.framgia.moviedb.feature.moviedetail;

import android.support.annotation.Nullable;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.BaseView;
import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.data.model.Movie;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.moviedetail
 */
public interface MovieDetailContract {
    interface View extends BaseView {
        void onPrepare();
        void onMovieDetailLoaded(Movie movie);
        void onMovieDetailError();
        void showCompanyDetailUi(Company company);
        void showVideoDetailUi(String key);
        void shareLink(String title, String key);
        void showMovieReviewUi(String title, String movieId);
    }

    interface Presenter extends BasePresenter {
        void loadDetail(String movieId);
        void openCompanyDetails(Company company);
        void updateFavorite(@Nullable String type, Movie movie);
        void openVideoDetail(String key);
        void shareLink(String title, String key);
        void openMovieReview(String title, String movieId);
    }
}
