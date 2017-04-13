package com.dev.trident.comicbookreader.fragments.singlepageview.presenter;

import com.dev.trident.comicbookreader.fragments.singlepageview.view.SinglepageFragmentView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for SinglepageFragment
 * @see com.dev.trident.comicbookreader.fragments.singlepageview.presenter.SinglepageFragmentPresenter
**/

public class SinglepageFragmentPresenterImpl implements SinglepageFragmentPresenter {
    private SinglepageFragmentView singlepageFragmentView;

    public SinglepageFragmentPresenterImpl(SinglepageFragmentView singlepageFragmentView) {
        this.singlepageFragmentView = singlepageFragmentView;
    }
}
