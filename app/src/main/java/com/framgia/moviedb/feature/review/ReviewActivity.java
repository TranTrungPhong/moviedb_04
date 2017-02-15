package com.framgia.moviedb.feature.review;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Review;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityReviewBinding;
import com.framgia.moviedb.feature.BaseActivity;
import com.framgia.moviedb.ui.adapter.ReviewAdapter;

import java.util.List;

public class ReviewActivity extends BaseActivity implements ReviewContract.View {
    private final static String EXTRA_TITLE = "EXTRA_TITLE";
    private final static String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private ActivityReviewBinding mReviewBinding;
    private ReviewContract.Presenter mReviewPresenter;
    private boolean mIsLoadMore, mIsEmptyData;
    private int mPastVisiblesItems, mTotalItemCount, mVisibleItemCount;
    private ObservableBoolean mIsLoading = new ObservableBoolean();
    private ObservableBoolean mIsError = new ObservableBoolean();
    private ObservableBoolean mIsNoReview = new ObservableBoolean();
    private ObservableField<ReviewAdapter> mReviewAdapter = new ObservableField<>();

    public static Intent getMovieReviewIntent(Context context, String title, String id) {
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_MOVIE_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review);
        mReviewPresenter = new ReviewPresenter(this, MovieRepository.getInstance(this));
        mReviewBinding.setPresenter(mReviewPresenter);
        mReviewBinding.setReviewActivity(this);
        start();
        mReviewPresenter.start();
    }

    @Override
    public void onPrepare() {
        if (mReviewAdapter.get().getItemCount() == 0) {
            mIsLoading.set(true);
            mIsError.set(false);
        }
        mReviewPresenter.loadReview(getIntent().getStringExtra(EXTRA_MOVIE_ID));
    }

    @Override
    public void onReviewEmpty() {
        mIsEmptyData = true;
        onReviewError();
    }

    @Override
    public void onReviewLoaded(List<Review> reviews) {
        mReviewAdapter.get().removeLoading();
        mIsLoadMore = false;
        mReviewAdapter.get().updateData(reviews);
        mIsLoading.set(false);
    }

    @Override
    public void onReviewError() {
        mReviewAdapter.get().removeLoading();
        mIsLoadMore = false;
        mIsLoading.set(false);
        if (mReviewAdapter.get().getItemCount() == 0 && !mIsEmptyData) {
            mIsError.set(true);
        } else if (mReviewAdapter.get().getItemCount() == 0 && mIsEmptyData) {
            mIsNoReview.set(true);
        }
    }

    @Override
    public void start() {
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        mReviewAdapter.set(new ReviewAdapter(this, null));
        mReviewBinding.recyclerReview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsEmptyData) return;
                LinearLayoutManager linearLayoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
                mVisibleItemCount = linearLayoutManager.getChildCount();
                mPastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                mTotalItemCount = linearLayoutManager.getItemCount();
                if (!mIsLoadMore &&
                    (mVisibleItemCount + mPastVisiblesItems) >= mTotalItemCount) {
                    mIsLoadMore = true;
                    mReviewAdapter.get().addLoading();
                    mReviewPresenter.start();
                }
            }
        });
    }

    public ObservableBoolean getIsError() {
        return mIsError;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsNoReview() {
        return mIsNoReview;
    }

    public ObservableField<ReviewAdapter> getReviewAdapter() {
        return mReviewAdapter;
    }
}
