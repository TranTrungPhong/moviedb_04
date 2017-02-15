package com.framgia.moviedb.feature.review;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.BaseView;
import com.framgia.moviedb.data.model.Review;

import java.util.List;

/**
 * Created by tuannt on 14/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.review
 */
public interface ReviewContract {
    interface View extends BaseView {
        void onPrepare();
        void onReviewEmpty();
        void onReviewLoaded(List<Review> reviews);
        void onReviewError();
    }

    interface Presenter extends BasePresenter {
        void loadReview(String movieId);
    }
}
