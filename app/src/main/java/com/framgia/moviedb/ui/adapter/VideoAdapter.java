package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Video;
import com.framgia.moviedb.databinding.ItemVideoBinding;
import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> mVideos = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MovieDetailContract.Presenter mListener;

    public VideoAdapter(Context context, List<Video> videos,
                        MovieDetailContract.Presenter listener) {
        if (mVideos != null) mVideos.addAll(videos);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoBinding mItemVideoBinding;
        private VideoItemAcitonHandler mVideoItemAcitonHandler;

        ViewHolder(ItemVideoBinding itemVideoBinding) {
            super(itemVideoBinding.getRoot());
            mItemVideoBinding = itemVideoBinding;
            mVideoItemAcitonHandler = new VideoItemAcitonHandler(mListener);
            mItemVideoBinding.setHandler(mVideoItemAcitonHandler);
        }

        void bindData(Video video) {
            if (video == null) return;
            mItemVideoBinding.setVideo(video);
            mItemVideoBinding.executePendingBindings();
        }
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoBinding itemVideoBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_video, parent, false);
        return new VideoAdapter.ViewHolder(itemVideoBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mVideos ? mVideos.size() : 0;
    }
}
