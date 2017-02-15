package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Review;
import com.framgia.moviedb.databinding.ItemLoadingBinding;
import com.framgia.moviedb.databinding.ItemReviewBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Review> mReviews = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public ReviewAdapter(Context context, List<Review> reviews) {
        if (reviews != null) mReviews.addAll(reviews);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<Review> reviews) {
        if (reviews != null) mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding mItemReviewBinding;

        ViewHolder(ItemReviewBinding itemReviewBinding) {
            super(itemReviewBinding.getRoot());
            mItemReviewBinding = itemReviewBinding;
        }

        private void bindData(Review review) {
            if (review == null) return;
            mItemReviewBinding.setReview(review);
            mItemReviewBinding.executePendingBindings();
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            ItemReviewBinding itemReviewBinding =
                DataBindingUtil
                    .inflate(mLayoutInflater, R.layout.item_review, parent, false);
            return new ReviewAdapter.ViewHolder(itemReviewBinding);
        } else if (viewType == VIEW_TYPE_LOADING) {
            ItemLoadingBinding itemLoadingBinding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_loading, parent, false);
            return new ReviewAdapter.LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReviewAdapter.ViewHolder) {
            ((ReviewAdapter.ViewHolder) holder).bindData(mReviews.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return null != mReviews ? mReviews.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mReviews.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public Review getLastItem() {
        return mReviews.get(mReviews.size() - 1);
    }

    public void addLoading() {
        mReviews.add(null);
        notifyItemInserted(mReviews.size() - 1);
    }

    public void removeLoading() {
        if (getItemCount() > 0 && getLastItem() == null) {
            mReviews.remove(mReviews.size() - 1);
            notifyDataSetChanged();
        }
    }
}
