package com.framgia.moviedb.feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by tuannt on 12/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
