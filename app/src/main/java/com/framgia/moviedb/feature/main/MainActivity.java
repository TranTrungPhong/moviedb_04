package com.framgia.moviedb.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

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
    implements MainContract.View {
    private MainContract.Presenter mMainPresenter;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainPresenter = new MainPresenter(
            this,
            GenreRepository.getInstance(this),
            MovieRepository.getInstance(this));
        mMainPresenter.start();
    }

    @Override
    public void onSuccess() {
        mMainBinding.setIsLoading(false);
    }

    @Override
    public void onGenresLoaded(List<Genre> genres) {
        GenreAdapter genreAdapter = new GenreAdapter(this, genres, mMainPresenter);
        mMainBinding.recyclerGenres.setAdapter(genreAdapter);
        mMainBinding.recyclerGenres.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.recyclerGenres.setHasFixedSize(true);
    }

    @Override
    public void onNowPlayingMoviesLoaded(List<Movie> movies) {
        HorizontalMovieAdapter nowPlayingAdapter =
            new HorizontalMovieAdapter(this, movies, mMainPresenter);
        mMainBinding.linearNowPlaying.recyclerView.setAdapter(nowPlayingAdapter);
        mMainBinding.linearNowPlaying.textCategory.setText(
            getResources().getText(R.string.title_now_playing));
        mMainBinding.linearNowPlaying.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearNowPlaying.recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onPopularMoviesLoaded(List<Movie> movies) {
        HorizontalMovieAdapter popularAdapter =
            new HorizontalMovieAdapter(this, movies, mMainPresenter);
        mMainBinding.linearPopular.recyclerView.setAdapter(popularAdapter);
        mMainBinding.linearPopular.textCategory.setText(
            getResources().getText(R.string.title_popular));
        mMainBinding.linearPopular.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearPopular.recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onTopRatedMoviesLoaded(List<Movie> movies) {
        HorizontalMovieAdapter topRatedAdapter =
            new HorizontalMovieAdapter(this, movies, mMainPresenter);
        mMainBinding.linearTopRated.recyclerView.setAdapter(topRatedAdapter);
        mMainBinding.linearTopRated.textCategory.setText(
            getResources().getText(R.string.title_top_rated));
        mMainBinding.linearTopRated.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearTopRated.recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onUpComingMoviesLoaded(List<Movie> movies) {
        HorizontalMovieAdapter upcomingAdapter =
            new HorizontalMovieAdapter(this, movies, mMainPresenter);
        mMainBinding.linearUpcoming.recyclerView.setAdapter(upcomingAdapter);
        mMainBinding.linearUpcoming.textCategory.setText(
            getResources().getText(R.string.title_upcoming));
        mMainBinding.linearUpcoming.recyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMainBinding.linearUpcoming.recyclerView.setHasFixedSize(true);
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
        mMainBinding.setMainPresenter(mMainPresenter);
    }

    @Override
    public void showGenreDetailsUi(Genre genre) {
        startActivity(
            MoviesActivity.getMoviesIntent(this, MoviesActivity.EXTRA_KEY, genre));
    }
}
