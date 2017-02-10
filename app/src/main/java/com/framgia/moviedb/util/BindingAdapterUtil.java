package com.framgia.moviedb.util;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.framgia.moviedb.R;
import com.framgia.moviedb.feature.main.MainContract;
import com.squareup.picasso.Picasso;

/**
 * Created by tuannt on 09/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.util
 */
public class BindingAdapterUtil {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("android:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view,
                                                              final MainContract.Presenter
                                                                  presenter) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @BindingAdapter("android:setCustomColor")
    public static void setCustomColor(SwipeRefreshLayout view, boolean b) {
        if (b) view.setColorSchemeColors(
            ContextCompat.getColor(view.getContext(), R.color.color_accent));
    }
}
