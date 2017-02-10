package com.framgia.moviedb.feature.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.feature.movies.MoviesActivity;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    public static Intent getMovieDetailIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        return intent;
    }
}
