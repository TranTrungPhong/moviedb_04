package com.framgia.moviedb.feature.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityMovieDetailBinding;
import com.framgia.moviedb.feature.BaseActivity;
import com.framgia.moviedb.feature.movies.MoviesActivity;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final int DEFAULT_POSITION = 1;
    private static final String SHARE_TYPE = "text/plain";
    private static final String SHARE_BASE_LINK = "https://www.youtube.com/watch?v=";
    private static final String SHARE_CHOOSE = "Choose one";
    private ActivityMovieDetailBinding mMovieDetailBinding;
    private MovieDetailContract.Presenter mMovieDetailPresenter;
    private Movie mMovie;
    private int mPosition;
    private boolean mOriginalFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mMovieDetailPresenter = new MovieDetailPresenter(
            this, MovieRepository.getInstance(this));
        mMovieDetailBinding.setPresenter(mMovieDetailPresenter);
        start();
        mMovieDetailPresenter.start();
    }

    public static Intent getMovieDetailIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        return intent;
    }

    public static Intent getMovieDetailIntent(Context context, int position, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMovie != null && mOriginalFavorite != mMovie.isFavorite()) {
            Intent intent = new Intent();
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovie);
            intent.putExtra(MovieDetailActivity.EXTRA_POSITION, mPosition);
            setResult(RESULT_OK, intent);
        } else
            setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void start() {
        getIntentData();
        setUpView();
    }

    private void setUpView() {
        setSupportActionBar(mMovieDetailBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mMovieDetailBinding.setMovie(mMovie);
    }

    private void getIntentData() {
        mMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        mOriginalFavorite = mMovie.isFavorite();
    }

    @Override
    public void onPrepare() {
        mMovieDetailPresenter.loadDetail(String.valueOf(mMovie.getId()));
    }

    @Override
    public void onMovieDetailLoaded(Movie movie) {
        // TODO: setup all view
    }

    @Override
    public void onMovieDetailError() {
        // TODO: notify error for user 
    }

    @Override
    public void showCompanyDetailUi(Company company) {
        startActivity(
            MoviesActivity.getMoviesIntent(this, Constant.IntentKey.EXTRA_COMPANY, company));
    }

    @Override
    public void showVideoDetailUi(String key) {
        // TODO: start activity video 
    }

    @Override
    public void shareLink(String title, String key) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(SHARE_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        String link = SHARE_BASE_LINK + key;
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, SHARE_CHOOSE));
    }

    @Override
    public void showMovieReviewUi(String title, String movieId) {
        // TODO: start activity review
    }
}
