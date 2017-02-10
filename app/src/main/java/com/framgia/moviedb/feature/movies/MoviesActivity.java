package com.framgia.moviedb.feature.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;

public class MoviesActivity extends AppCompatActivity {
    public static final String EXTRA_GENRES = "EXTRA_GENRES";
    public static final String EXTRA_KEY = "EXTRA_KEY";
    public static final String EXTRA_SEARCH = "EXTRA_SEARCH";
    public static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";
    public static final String EXTRA_LIST_MOVIES = "EXTRA_LIST_MOVIES";
    public static final String EXTRA_COMPANY = "EXTRA_COMPANY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    public static Intent getMoviesIntent(Context context, String key, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(MoviesActivity.EXTRA_GENRES, genre);
        intent.putExtra(MoviesActivity.EXTRA_KEY, key);
        return intent;
    }

    public static Intent getMoviesIntent(Context context, String key, String query) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(MoviesActivity.EXTRA_KEY, key);
        intent.putExtra(MoviesActivity.EXTRA_SEARCH, query);
        return intent;
    }

    public static Intent getMoviesIntent(Context context, String key) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(MoviesActivity.EXTRA_KEY, key);
        return intent;
    }
}
