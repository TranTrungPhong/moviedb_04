package com.framgia.moviedb.feature.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.feature.BaseActivity;

public class MovieDetailActivity extends BaseActivity {
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final int DEFAULT_POSITION = 1;
    private Movie mMovie;
    private int mPosition;
    private boolean mOriginalFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

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
}
