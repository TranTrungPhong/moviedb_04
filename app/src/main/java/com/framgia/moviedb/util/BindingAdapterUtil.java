package com.framgia.moviedb.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

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
}
