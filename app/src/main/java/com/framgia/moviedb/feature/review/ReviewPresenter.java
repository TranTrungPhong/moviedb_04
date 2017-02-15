package com.framgia.moviedb.feature.review;

import com.framgia.moviedb.data.model.Review;
import com.framgia.moviedb.data.source.MovieDataSource;

import java.util.List;

/**
 * Created by tuannt on 14/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.review
 */
public class ReviewPresenter implements ReviewContract.Presenter {
    private ReviewContract.View mReviewView;
    private MovieDataSource mMovieRepository;
    private int mPage = 1;

    public ReviewPresenter(ReviewContract.View reviewView,
                           MovieDataSource movieRepository) {
        mReviewView = reviewView;
        mMovieRepository = movieRepository;
    }

    @Override
    public void loadReview(String movieId) {
        mMovieRepository.loadMovieReview(movieId, String.valueOf(mPage),
            new MovieDataSource.GetReviewCallback() {
                @Override
                public void onLoaded(List<Review> datas) {
                    mPage++;
                    mReviewView.onReviewLoaded(datas);
                }

                @Override
                public void onEmpty() {
                    mReviewView.onReviewEmpty();
                }

                @Override
                public void onNotAvailable() {
                    mReviewView.onReviewError();
                }
            });
    }

    @Override
    public void start() {
        mReviewView.onPrepare();
    }
}
