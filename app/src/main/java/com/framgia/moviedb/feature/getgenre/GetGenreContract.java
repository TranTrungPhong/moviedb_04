package com.framgia.moviedb.feature.getgenre;

import com.framgia.moviedb.BasePresenter;
import com.framgia.moviedb.data.model.Genre;

import java.util.List;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.getgenre
 */
public interface GetGenreContract {
    interface View {
        void onSaveGenre(List<Genre> genres);
        void onError();
    }

    interface Presenter extends BasePresenter {
    }
}
