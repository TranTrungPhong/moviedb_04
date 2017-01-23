package com.framgia.moviedb.feature.getgenre;

import com.framgia.moviedb.service.ServiceGenerator;
import com.framgia.moviedb.service.genre.ApiListGenre;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.getgenre
 */
public class GetGenrePresenter implements GetGenreContract.Presenter {
    private GetGenreContract.View mGetGenreView;

    public GetGenrePresenter(GetGenreContract.View getGenreView) {
        mGetGenreView = getGenreView;
    }

    @Override
    public void start() {
        ApiListGenre.ListGenres listGenres = ServiceGenerator.createService(
            ApiListGenre.ListGenres.class);

        Call<ApiListGenre.Response> call = listGenres.loadGenres(ApiListGenre.param());

        call.enqueue(new Callback<ApiListGenre.Response>() {
            @Override
            public void onResponse(Call<ApiListGenre.Response> call,
                                   Response<ApiListGenre.Response> response) {
                if (response != null) mGetGenreView.onSaveGenre(response.body().getGenres());
                else mGetGenreView.onError();
            }

            @Override
            public void onFailure(Call<ApiListGenre.Response> call, Throwable t) {
                mGetGenreView.onError();
            }
        });
    }
}
