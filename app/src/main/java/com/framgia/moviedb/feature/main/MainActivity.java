package com.framgia.moviedb.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.GenreRepository;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityMainBinding;
import com.framgia.moviedb.feature.movies.MoviesActivity;
import com.framgia.moviedb.ui.adapter.GenreAdapter;
import com.framgia.moviedb.ui.adapter.HorizontalMovieAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity
    implements MainContract.View, SearchView.OnQueryTextListener {
    private MainContract.Presenter mMainPresenter;
    private ActivityMainBinding mMainBinding;
    private GenreAdapter mGenreAdapter;
    private HorizontalMovieAdapter mNowPlayingAdapter, mPopularAdapter,
        mTopRatedAdapter, mUpcomingAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainPresenter = new MainPresenter(
            this,
            GenreRepository.getInstance(this),
            MovieRepository.getInstance(this));
        mMainBinding.setMainPresenter(mMainPresenter);
        start();
        mMainPresenter.start();
    }

    @Override
    public void start() {
        initListGenres();
        initListNowPlayingMovies();
        initListPopularMovies();
        initListTopRated();
        initListUpcomingMovies();
    }

    private void initListGenres() {
        mGenreAdapter = new GenreAdapter(this, null, mMainPresenter);
        mMainBinding.recyclerGenres.setAdapter(mGenreAdapter);
        mMainBinding.recyclerGenres.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.recyclerGenres.setHasFixedSize(true);
    }

    private void initListNowPlayingMovies() {
        mNowPlayingAdapter =
            new HorizontalMovieAdapter(this, null, mMainPresenter);
        mMainBinding.linearNowPlaying.recyclerView.setAdapter(mNowPlayingAdapter);
        mMainBinding.linearNowPlaying.textCategory.setText(
            getResources().getText(R.string.title_now_playing));
        mMainBinding.linearNowPlaying.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearNowPlaying.recyclerView.setHasFixedSize(true);
    }

    private void initListPopularMovies() {
        mPopularAdapter =
            new HorizontalMovieAdapter(this, null, mMainPresenter);
        mMainBinding.linearPopular.recyclerView.setAdapter(mPopularAdapter);
        mMainBinding.linearPopular.textCategory.setText(
            getResources().getText(R.string.title_popular));
        mMainBinding.linearPopular.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearPopular.recyclerView.setHasFixedSize(true);
    }

    private void initListTopRated() {
        mTopRatedAdapter =
            new HorizontalMovieAdapter(this, null, mMainPresenter);
        mMainBinding.linearTopRated.recyclerView.setAdapter(mTopRatedAdapter);
        mMainBinding.linearTopRated.textCategory.setText(
            getResources().getText(R.string.title_top_rated));
        mMainBinding.linearTopRated.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearTopRated.recyclerView.setHasFixedSize(true);
    }

    private void initListUpcomingMovies() {
        mUpcomingAdapter =
            new HorizontalMovieAdapter(this, null, mMainPresenter);
        mMainBinding.linearUpcoming.recyclerView.setAdapter(mUpcomingAdapter);
        mMainBinding.linearUpcoming.textCategory.setText(
            getResources().getText(R.string.title_upcoming));
        mMainBinding.linearUpcoming.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearUpcoming.recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onSuccess() {
        mMainBinding.setIsLoading(false);
    }

    @Override
    public void onGenresLoaded(List<Genre> genres) {
        mGenreAdapter.updateData(genres);
    }

    @Override
    public void onNowPlayingMoviesLoaded(List<Movie> movies) {
        mNowPlayingAdapter.updateData(movies);
    }

    @Override
    public void onPopularMoviesLoaded(List<Movie> movies) {
        mPopularAdapter.updateData(movies);
    }

    @Override
    public void onTopRatedMoviesLoaded(List<Movie> movies) {
        mTopRatedAdapter.updateData(movies);
    }

    @Override
    public void onUpComingMoviesLoaded(List<Movie> movies) {
        mUpcomingAdapter.updateData(movies);
    }

    @Override
    public void onPrepare() {
        mMainBinding.setIsLoading(true);
        mMainBinding.setIsError(false);
    }

    @Override
    public void onError() {
        mMainBinding.setIsLoading(false);
        mMainBinding.setIsError(true);
    }

    @Override
    public void showGenreDetailsUi(Genre genre) {
        startActivity(
            MoviesActivity.getMoviesIntent(this, MoviesActivity.EXTRA_GENRES, genre));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            startActivity(MoviesActivity.getMoviesIntent(this, MoviesActivity.EXTRA_FAVORITE));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        startActivity(MoviesActivity.getMoviesIntent(this, MoviesActivity.EXTRA_SEARCH, query));
        mSearchView.onActionViewCollapsed();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onRefreshDone() {
        mMainBinding.swipeRefresh.setRefreshing(false);
    }
}
