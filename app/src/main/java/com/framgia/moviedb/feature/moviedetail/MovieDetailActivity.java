package com.framgia.moviedb.feature.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.model.Video;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityMovieDetailBinding;
import com.framgia.moviedb.feature.BaseActivity;
import com.framgia.moviedb.feature.movies.MoviesActivity;
import com.framgia.moviedb.feature.review.ReviewActivity;
import com.framgia.moviedb.feature.video.VideoActivity;
import com.framgia.moviedb.service.ApiCore;
import com.framgia.moviedb.ui.adapter.GenreAdapter;
import com.framgia.moviedb.ui.adapter.VideoAdapter;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final int DEFAULT_POSITION = 1;
    private static final String SHARE_TYPE = "text/plain";
    private static final String SHARE_BASE_LINK = "https://www.youtube.com/watch?v=";
    private static final String SHARE_CHOOSE = "Choose one";
    private ActivityMovieDetailBinding mMovieDetailBinding;
    private MovieDetailContract.Presenter mMovieDetailPresenter;
    private int mPosition;
    private boolean mOriginalFavorite;
    private Movie mMovie;
    private ObservableField<GenreAdapter> mGenreAdapter = new ObservableField<>();
    private ObservableField<VideoAdapter> mVideoAdapter = new ObservableField<>();
    private ObservableBoolean mIsShowShare = new ObservableBoolean();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        mMovieDetailPresenter = new MovieDetailPresenter(
            this, MovieRepository.getInstance(this));
        mMovieDetailBinding.setPresenter(mMovieDetailPresenter);
        mMovieDetailBinding.setMovieDetail(this);
        start();
        mMovieDetailPresenter.start();
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
    }

    private void getIntentData() {
        mMovie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        mMovie.setFavorite(mMovieDetailPresenter.checkFavorite(mMovie));
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        mOriginalFavorite = mMovie.isFavorite();
    }

    @Override
    public void onPrepare() {
        mMovieDetailPresenter.loadDetail(String.valueOf(mMovie.getId()));
    }

    @Override
    public void onMovieDetailLoaded(Movie movie) {
        updateCurrentMovie(movie);
        setUpListGenres();
        setUpListVideos(movie);
    }

    private void updateCurrentMovie(Movie movie) {
        if (movie.getVideo().getVideos() != null && movie.getVideo().getVideos().size() > 0) {
            mIsShowShare.set(true);
            mMovie.setVideo(movie.getVideo().getVideos().get(0));
        }
        mMovie.setReleaseDate(movie.getReleaseDate());
        mMovie.setGenres(movie.getGenres());
        mMovie.setCompanies(movie.getCompanies());
        mMovie.setBackdrop(movie.getBackdrop());
    }

    private void setUpListGenres() {
        mGenreAdapter.set(new GenreAdapter(this, mMovie.getGenres(), null));
    }

    private void setUpListVideos(Movie movie) {
        for (Video video : movie.getVideo().getVideos()) {
            video.setAvatar(ApiCore.BASE_IMAGE_URL + (movie.getBackdrop() != null ? movie
                .getBackdrop() : movie.getPoster()));
        }
        mVideoAdapter.set(new VideoAdapter(this, movie.getVideo().getVideos(),
            mMovieDetailPresenter));
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
        startActivity(VideoActivity.getVideoDetailIntent(this, key));
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
        startActivity(ReviewActivity.getMovieReviewIntent(this, title, movieId));
    }

    public ObservableBoolean getIsShowShare() {
        return mIsShowShare;
    }

    public ObservableField<GenreAdapter> getGenreAdapter() {
        return mGenreAdapter;
    }

    public Movie getMovie() {
        return mMovie;
    }

    public ObservableField<VideoAdapter> getVideoAdapter() {
        return mVideoAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMovie.setFavorite(mMovieDetailPresenter.checkFavorite(mMovie));
    }
}
