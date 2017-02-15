package com.framgia.moviedb.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.databinding.ItemCompanyBinding;
import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private List<Company> mCompanys = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MovieDetailContract.Presenter mListener;

    public CompanyAdapter(Context context, List<Company> companies,
                          MovieDetailContract.Presenter listener) {
        if (companies != null) mCompanys.addAll(companies);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCompanyBinding mItemCompanyBinding;
        private CompanyItemActionHandler mCompanyItemActionHandler;

        ViewHolder(ItemCompanyBinding itemCompanyBinding) {
            super(itemCompanyBinding.getRoot());
            mItemCompanyBinding = itemCompanyBinding;
            mCompanyItemActionHandler = new CompanyItemActionHandler(mListener);
            mItemCompanyBinding.setHandler(mCompanyItemActionHandler);
        }

        void bindData(Company company) {
            if (company == null) return;
            mItemCompanyBinding.setCompany(company);
            mItemCompanyBinding.executePendingBindings();
        }
    }

    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCompanyBinding itemCompanyBinding =
            DataBindingUtil.inflate(mLayoutInflater, R.layout.item_company, parent, false);
        return new CompanyAdapter.ViewHolder(itemCompanyBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mCompanys.get(position));
    }

    @Override
    public int getItemCount() {
        return null != mCompanys ? mCompanys.size() : 0;
    }
}
