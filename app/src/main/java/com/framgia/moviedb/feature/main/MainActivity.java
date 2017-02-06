package com.framgia.moviedb.feature.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.GenreRepository;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.databinding.ActivityMainBinding;

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
            GenreRepository.getInstance(),
            MovieRepository.getInstance());
        mMainPresenter.start();
    }

    @Override
    public void onSuccess() {
        mMainBinding.progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void onGenresLoaded(List<Genre> genres) {
        // TODO: update genre view 
    }

    @Override
    public void onNowPlayingMoviesLoaded(List<Movie> movies) {
        // TODO: update now playing movie 
    }

    @Override
    public void onPopularMoviesLoaded(List<Movie> movies) {
        // TODO: update popular movie 
    }

    @Override
    public void onTopRatedMoviesLoaded(List<Movie> movies) {
        // TODO: update top rated movie 
    }

    @Override
    public void onUpComingMoviesLoaded(List<Movie> movies) {
        // TODO: update upcoming movie
    }

    @Override
    public void onError() {
        // TODO: notify error for user
    }
}
