package com.framgia.moviedb.feature.movies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityMoviesBinding;
import com.framgia.moviedb.feature.BaseActivity;
import com.framgia.moviedb.feature.moviedetail.MovieDetailActivity;
import com.framgia.moviedb.ui.adapter.VerticalMovieAdapter;

import java.util.List;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {
    private ActivityMoviesBinding mMoviesBinding;
    private MoviesContract.Presenter mMoviesPresenter;
    private VerticalMovieAdapter mVerticalMovieAdapter;
    private boolean mIsLoadMore;
    private int mPastVisiblesItems, mTotalItemCount, mVisibleItemCount;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_movies);
        mMoviesPresenter = new MoviesPresenter(
            this, MovieRepository.getInstance(this));
        mMoviesBinding.setPresenter(mMoviesPresenter);
        start();
        mMoviesPresenter.start();
    }

    public static Intent getMoviesIntent(Context context, String key, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constant.IntentKey.EXTRA_GENRES, genre);
        intent.putExtra(Constant.IntentKey.EXTRA_KEY, key);
        return intent;
    }

    public static Intent getMoviesIntent(Context context, String key, String query) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constant.IntentKey.EXTRA_KEY, key);
        intent.putExtra(Constant.IntentKey.EXTRA_SEARCH, query);
        return intent;
    }

    public static Intent getMoviesIntent(Context context, String key) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constant.IntentKey.EXTRA_KEY, key);
        return intent;
    }

    @Override
    public void start() {
        setUpTitle();
        setUpView();
    }

    private void setUpView() {
        mVerticalMovieAdapter = new VerticalMovieAdapter(this, null, mMoviesPresenter);
        mMoviesBinding.recyclerView.setAdapter(mVerticalMovieAdapter);
        final LinearLayoutManager linearLayoutManager =
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMoviesBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mMoviesBinding.recyclerView.setHasFixedSize(true);
        if (!mType.equals(Constant.IntentKey.EXTRA_FAVORITE))
            mMoviesBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mVisibleItemCount = linearLayoutManager.getChildCount();
                    mPastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    if (!mIsLoadMore &&
                        (mVisibleItemCount + mPastVisiblesItems) >= mTotalItemCount) {
                        mIsLoadMore = true;
                        mVerticalMovieAdapter.addLoading();
                        mMoviesPresenter.start();
                    }
                }
            });
    }

    private void setUpTitle() {
        mType = getIntent().getStringExtra(Constant.IntentKey.EXTRA_KEY);
        switch (mType) {
            case Constant.IntentKey.EXTRA_GENRES:
                Genre genre = (Genre) getIntent().getSerializableExtra(
                    Constant.IntentKey.EXTRA_GENRES);
                getSupportActionBar().setTitle(genre.getName());
                break;
            case Constant.IntentKey.EXTRA_NOW_PLAYING_MOVIES:
                getSupportActionBar()
                    .setTitle(getResources().getString(R.string.title_now_playing));
                break;
            case Constant.IntentKey.EXTRA_POPULAR_MOVIES:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_popular));
                break;
            case Constant.IntentKey.EXTRA_TOP_RATED_MOVIES:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_top_rated));
                break;
            case Constant.IntentKey.EXTRA_UPCOMING_MOVIES:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_upcoming));
                break;
            case Constant.IntentKey.EXTRA_FAVORITE:
                getSupportActionBar().setTitle(getResources().getString(R.string.title_favorite));
                break;
            case Constant.IntentKey.EXTRA_SEARCH:
                String query = getIntent().getStringExtra(Constant.IntentKey.EXTRA_SEARCH);
                getSupportActionBar().setTitle(getResources().getString(R.string.title_search) +
                    " " + query);
                break;
            case Constant.IntentKey.EXTRA_COMPANY:
                Company company = (Company) getIntent().getSerializableExtra(
                    Constant.IntentKey.EXTRA_COMPANY);
                getSupportActionBar().setTitle(company.getName());
                break;
            default:
                break;
        }
    }

    @Override
    public void onPrepare() {
        if (mVerticalMovieAdapter.getItemCount() == 0) {
            mMoviesBinding.setIsLoading(true);
            mMoviesBinding.setIsError(false);
        }
        loadData();
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        mVerticalMovieAdapter.removeLoading();
        mIsLoadMore = false;
        mVerticalMovieAdapter.updateData(movies);
        mMoviesBinding.setIsLoading(false);
    }

    @Override
    public void onMoviesNotAvailable() {
        mVerticalMovieAdapter.removeLoading();
        mIsLoadMore = false;
        mMoviesBinding.setIsLoading(false);
        if (mType.equals(
            Constant.IntentKey.EXTRA_FAVORITE)) {
            mMoviesBinding.textMessageNoItem.setVisibility(View.VISIBLE);
        } else if (mVerticalMovieAdapter.getItemCount() == 0) {
            mMoviesBinding.setIsError(true);
        }
    }

    @Override
    public void showMovieDetailsUi(int position, Movie movie) {
        startActivityForResult(MovieDetailActivity.getMovieDetailIntent(this, position, movie),
            Constant.MOVIES_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void removeItemFavorite(int position, Movie movie) {
        mVerticalMovieAdapter.deleteItem(position);
        if (mVerticalMovieAdapter.getItemCount() == 0) {
            mMoviesBinding.textMessageNoItem.setVisibility(View.VISIBLE);
        }
    }

    private void loadData() {
        switch (mType) {
            case Constant.IntentKey.EXTRA_GENRES:
                Genre genre = (Genre) getIntent().getSerializableExtra(
                    Constant.IntentKey.EXTRA_GENRES);
                mMoviesPresenter
                    .loadMovies(Constant.IntentKey.EXTRA_GENRES, String.valueOf(genre.getId()));
                break;
            case Constant.IntentKey.EXTRA_NOW_PLAYING_MOVIES:
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_NOW_PLAYING_MOVIES, null);
                break;
            case Constant.IntentKey.EXTRA_POPULAR_MOVIES:
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_POPULAR_MOVIES, null);
                break;
            case Constant.IntentKey.EXTRA_TOP_RATED_MOVIES:
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_TOP_RATED_MOVIES, null);
                break;
            case Constant.IntentKey.EXTRA_UPCOMING_MOVIES:
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_UPCOMING_MOVIES, null);
                break;
            case Constant.IntentKey.EXTRA_FAVORITE:
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_FAVORITE, null);
                break;
            case Constant.IntentKey.EXTRA_SEARCH:
                String query = getIntent().getStringExtra(Constant.IntentKey.EXTRA_SEARCH);
                mMoviesPresenter.loadMovies(Constant.IntentKey.EXTRA_SEARCH, query);
                break;
            case Constant.IntentKey.EXTRA_COMPANY:
                Company company = (Company) getIntent().getSerializableExtra(
                    Constant.IntentKey.EXTRA_COMPANY);
                mMoviesPresenter
                    .loadMovies(Constant.IntentKey.EXTRA_COMPANY, String.valueOf(company.getId()));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MOVIES_ACTIVITY_REQUEST_CODE &&
            resultCode == RESULT_OK) {
            Movie movie = (Movie) data.getSerializableExtra(MovieDetailActivity.EXTRA_MOVIE);
            int position = data.getIntExtra(MovieDetailActivity.EXTRA_POSITION,
                MovieDetailActivity.DEFAULT_POSITION);
            mVerticalMovieAdapter.updateItem(position, movie);
            if (mType.equals(Constant.IntentKey.EXTRA_FAVORITE)) {
                removeItemFavorite(position, movie);
            }
        }
    }
}
