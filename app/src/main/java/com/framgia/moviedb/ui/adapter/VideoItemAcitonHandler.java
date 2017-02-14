package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

/**
 * Created by tuannt on 14/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class VideoItemAcitonHandler {
    private MovieDetailContract.Presenter mListener;

    public VideoItemAcitonHandler(MovieDetailContract.Presenter listener) {
        mListener = listener;
    }

    public void videoClicked(String key) {
        if (mListener != null) mListener.openVideoDetail(key);
    }
}
