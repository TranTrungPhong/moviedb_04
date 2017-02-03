package com.framgia.moviedb.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.databinding.ActivitySplashBinding;
import com.framgia.moviedb.feature.getgenre.GetGenreContract;
import com.framgia.moviedb.feature.getgenre.GetGenrePresenter;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements
    GetGenreContract.View {

    private GetGenreContract.Presenter mGetGenrePresenter;

    private ActivitySplashBinding mSplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mGetGenrePresenter = new GetGenrePresenter(this);
        mGetGenrePresenter.start();
    }

    @Override
    public void onSaveGenre(List<Genre> genres) {
        mSplashBinding.progressSplash.setVisibility(View.GONE);
        // TODO: save genres
    }

    @Override
    public void onError() {
        // TODO: notify error for user
    }
}
