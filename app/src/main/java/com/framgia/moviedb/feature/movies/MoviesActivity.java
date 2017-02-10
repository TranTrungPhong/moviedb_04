package com.framgia.moviedb.feature.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.Constant;
import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;

public class MoviesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
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
}
