package com.framgia.moviedb.ui.adapter;

import com.framgia.moviedb.data.model.Company;
import com.framgia.moviedb.feature.moviedetail.MovieDetailContract;

/**
 * Created by tuannt on 13/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.adapter
 */
public class CompanyItemActionHandler {
    private MovieDetailContract.Presenter mListener;

    public CompanyItemActionHandler(MovieDetailContract.Presenter listener) {
        mListener = listener;
    }

    public void companyClicked(Company company) {
        if (mListener != null) mListener.openCompanyDetails(company);
    }
}
