package com.obser.googlemarket.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.obser.googlemarket.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppFragment extends BaseFragment {


    public AppFragment() {
        // Required empty public constructor
    }


    @Override
    protected View createSuccessView() {
        return null;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.error;
    }

}
