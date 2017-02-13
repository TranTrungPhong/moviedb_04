package com.framgia.moviedb.data.source;

import com.framgia.moviedb.data.model.Genre;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public interface GenreDataSource extends DataSource<Genre> {
    void getGenres(boolean isRefresh, GetCallback getCallback);
    void saveGenre(Genre data);
    void deleteAllGenres();
}
