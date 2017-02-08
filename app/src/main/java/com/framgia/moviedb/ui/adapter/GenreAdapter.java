package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.databinding.ItemGenreBinding;
import com.framgia.moviedb.feature.main.MainContract;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Genre> mGenres;
    private LayoutInflater mLayoutInflater;
    private MainContract.Presenter mListener;

    public GenreAdapter(Context context, List<Genre> genres, MainContract.Presenter listener) {
        mGenres = genres;
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemGenreBinding mItemGenreBinding;

        ViewHolder(ItemGenreBinding itemGenreBinding) {
            super(itemGenreBinding.getRoot());
            mItemGenreBinding = itemGenreBinding;
        }

        void bindData(Genre genre) {
            if (genre == null) return;
            GenreItemActionHandler itemActionHandler =
                new GenreItemActionHandler(mListener);
            mItemGenreBinding.setHandler(itemActionHandler);
            mItemGenreBinding.setGenre(genre);
            mItemGenreBinding.executePendingBindings();
        }
    }

    @Override
    public GenreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGenreBinding itemGenreBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_genre, parent, false);
        return new GenreAdapter.ViewHolder(itemGenreBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mGenres ? mGenres.size() : 0;
    }
}
