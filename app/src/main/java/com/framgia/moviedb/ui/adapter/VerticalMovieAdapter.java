package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.databinding.ItemLoadingBinding;
import com.framgia.moviedb.databinding.ItemVerticalMovieBinding;
import com.framgia.moviedb.feature.movies.MoviesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuannt on 12/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class VerticalMovieAdapter
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MoviesContract.Presenter mListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public VerticalMovieAdapter(Context context, List<Movie> movies,
                                MoviesContract.Presenter listener) {
        if (movies != null) mMovies.addAll(movies);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    public void updateData(List<Movie> movies) {
        if (movies != null) mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVerticalMovieBinding mItemVerticalMovieBinding;

        ViewHolder(ItemVerticalMovieBinding itemVerticalMovieBinding) {
            super(itemVerticalMovieBinding.getRoot());
            mItemVerticalMovieBinding = itemVerticalMovieBinding;
        }

        void bindData(int position, Movie movie) {
            if (movie == null) return;
            VerticalMovieItemActionHandler movieItemActionHandler =
                new VerticalMovieItemActionHandler(mListener);
            mItemVerticalMovieBinding.setHandler(movieItemActionHandler);
            mItemVerticalMovieBinding.setMovie(movie);
            mItemVerticalMovieBinding.setPosition(position);
            mItemVerticalMovieBinding.executePendingBindings();
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ItemLoadingBinding mItemLoadingBinding;

        public LoadingViewHolder(ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            mItemLoadingBinding = itemLoadingBinding;
        }

        void bindLoading() {
            mItemLoadingBinding.progressLoading.setIndeterminate(true);
            mItemLoadingBinding.executePendingBindings();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            ItemVerticalMovieBinding itemVerticalMovieBinding =
                DataBindingUtil
                    .inflate(mLayoutInflater, R.layout.item_vertical_movie, parent, false);
            return new VerticalMovieAdapter.ViewHolder(itemVerticalMovieBinding);
        } else if (viewType == VIEW_TYPE_LOADING) {
            ItemLoadingBinding itemLoadingBinding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_loading, parent, false);
            return new VerticalMovieAdapter.LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindData(position, mMovies.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            ((LoadingViewHolder) holder).bindLoading();
        }
    }

    @Override
    public int getItemCount() {
        return null != mMovies ? mMovies.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void updateItem(int position, Movie movie) {
        mMovies.get(position).setFavorite(movie.isFavorite());
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        mMovies.remove(position);
        notifyDataSetChanged();
    }

    public Movie getLastItem() {
        return mMovies.get(mMovies.size() - 1);
    }

    public void addLoading() {
        mMovies.add(null);
        notifyItemInserted(mMovies.size() - 1);
    }

    public void removeLoading() {
        if (getItemCount() > 0 && getLastItem() == null) {
            mMovies.remove(mMovies.size() - 1);
            notifyDataSetChanged();
        }
    }
}
