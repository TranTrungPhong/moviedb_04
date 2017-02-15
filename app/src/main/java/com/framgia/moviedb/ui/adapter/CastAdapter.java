package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Cast;
import com.framgia.moviedb.databinding.ItemCastBinding;
import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

import java.util.ArrayList;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> mCasts = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MovieDetailContract.Presenter mListener;

    public CastAdapter(Context context, List<Cast> casts, MovieDetailContract.Presenter listener) {
        if (mCasts != null) mCasts.addAll(casts);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCastBinding mItemCastBinding;

        ViewHolder(ItemCastBinding itemCastBinding) {
            super(itemCastBinding.getRoot());
            mItemCastBinding = itemCastBinding;
        }

        void bindData(Cast cast) {
            if (cast == null) return;
            mItemCastBinding.setCast(cast);
            mItemCastBinding.executePendingBindings();
        }
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCastBinding itemCastBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_cast, parent, false);
        return new CastAdapter.ViewHolder(itemCastBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mCasts ? mCasts.size() : 0;
    }
}
