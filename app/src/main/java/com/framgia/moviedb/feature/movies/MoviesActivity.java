package com.framgia.moviedb.feature.movies;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
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
    private boolean mIsLoadMore, mIsEmptyData;
    private int mPastVisiblesItems, mTotalItemCount, mVisibleItemCount;
    private String mType;
    private ObservableBoolean mIsLoading = new ObservableBoolean();
    private ObservableBoolean mIsError = new ObservableBoolean();
    private ObservableBoolean mIsNoFavoriteItem = new ObservableBoolean();
    private ObservableBoolean mIsNoSearchResult = new ObservableBoolean();
    private ObservableField<VerticalMovieAdapter> mVerticalMovieAdapter = new ObservableField<>();

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

    public static Intent getMoviesIntent(Context context, String key, Company company) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(Constant.IntentKey.EXTRA_COMPANY, company);
        intent.putExtra(Constant.IntentKey.EXTRA_KEY, key);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_movies);
        mMoviesPresenter = new MoviesPresenter(
            this, MovieRepository.getInstance(this));
        mMoviesBinding.setPresenter(mMoviesPresenter);
        mMoviesBinding.setMoviesActivity(this);
        start();
        mMoviesPresenter.start();
    }

    @Override
    public void start() {
        setUpTitle();
        setUpView();
    }

    private void setUpView() {
        mVerticalMovieAdapter.set(new VerticalMovieAdapter(this, null, mMoviesPresenter));
        if (!mType.equals(Constant.IntentKey.EXTRA_FAVORITE))
            mMoviesBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        mVerticalMovieAdapter.get().addLoading();
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
        if (mVerticalMovieAdapter.get().getItemCount() == 0) {
            mIsLoading.set(true);
            mIsError.set(false);
        }
        loadData();
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        mVerticalMovieAdapter.get().removeLoading();
        mIsLoadMore = false;
        mVerticalMovieAdapter.get().updateData(movies);
        mIsLoading.set(false);
    }

    @Override
    public void onMoviesNotAvailable() {
        mVerticalMovieAdapter.get().removeLoading();
        mIsLoadMore = false;
        mIsLoading.set(false);
        if (mType.equals(Constant.IntentKey.EXTRA_FAVORITE)) {
            mIsNoFavoriteItem.set(true);
        } else if (mType.equals(Constant.IntentKey.EXTRA_SEARCH) &&
            mVerticalMovieAdapter.get().getItemCount() == 0) {
            mIsNoSearchResult.set(true);
        } else if (mVerticalMovieAdapter.get().getItemCount() == 0) {
            mIsError.set(true);
        }
    }

    @Override
    public void onMoviesEmpty() {
        mIsEmptyData = true;
    }

    @Override
    public void showMovieDetailsUi(int position, Movie movie) {
        startActivityForResult(MovieDetailActivity.getMovieDetailIntent(this, position, movie),
            Constant.MOVIES_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void removeItemFavorite(int position, Movie movie) {
        if (mType.equals(Constant.IntentKey.EXTRA_FAVORITE)) {
            mVerticalMovieAdapter.get().deleteItem(position);
            if (mVerticalMovieAdapter.get().getItemCount() == 0) {
                mIsNoFavoriteItem.set(true);
            }
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

    public ObservableBoolean getIsError() {
        return mIsError;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsNoFavoriteItem() {
        return mIsNoFavoriteItem;
    }

    public ObservableBoolean getIsNoSearchResult() {
        return mIsNoSearchResult;
    }

    public ObservableField<VerticalMovieAdapter> getVerticalMovieAdapter() {
        return mVerticalMovieAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MOVIES_ACTIVITY_REQUEST_CODE &&
            resultCode == RESULT_OK) {
            Movie movie = (Movie) data.getSerializableExtra(MovieDetailActivity.EXTRA_MOVIE);
            int position = data.getIntExtra(MovieDetailActivity.EXTRA_POSITION,
                MovieDetailActivity.DEFAULT_POSITION);
            mVerticalMovieAdapter.get().updateItem(position, movie);
            removeItemFavorite(position, movie);
        }
    }
}
