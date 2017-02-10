package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.databinding.ItemHorizontalMovieBinding;
import com.framgia.moviedb.feature.main.MainContract;

import java.util.ArrayList;
import java.util.List;

public class HorizontalMovieAdapter
    extends RecyclerView.Adapter<HorizontalMovieAdapter.ViewHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MainContract.Presenter mListener;

    public HorizontalMovieAdapter(Context context, List<Movie> movies,
                                  MainContract.Presenter listener) {
        if (movies != null) mMovies.addAll(movies);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    public void updateData(List<Movie> movies) {
        mMovies.clear();
        if (movies != null) mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHorizontalMovieBinding mItemHorizontalMovieBinding;

        ViewHolder(ItemHorizontalMovieBinding itemHorizontalMovieBinding) {
            super(itemHorizontalMovieBinding.getRoot());
            mItemHorizontalMovieBinding = itemHorizontalMovieBinding;
        }

        void bindData(Movie movie) {
            if (movie == null) return;
            MovieItemActionHandler movieItemActionHandler =
                new MovieItemActionHandler(mListener);
            mItemHorizontalMovieBinding.setHandler(movieItemActionHandler);
            mItemHorizontalMovieBinding.setMovie(movie);
            mItemHorizontalMovieBinding.executePendingBindings();
        }
    }

    @Override
    public HorizontalMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHorizontalMovieBinding itemHorizontalMovieBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_horizontal_movie, parent, false);
        return new HorizontalMovieAdapter.ViewHolder(itemHorizontalMovieBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mMovies ? mMovies.size() : 0;
    }
}
