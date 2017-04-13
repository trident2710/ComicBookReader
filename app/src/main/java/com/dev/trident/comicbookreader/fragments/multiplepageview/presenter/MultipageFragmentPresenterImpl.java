package com.dev.trident.comicbookreader.fragments.multiplepageview.presenter;

import com.dev.trident.comicbookreader.fragments.multiplepageview.view.MultipageFragmentView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for MultipageFragment
 * @see com.dev.trident.comicbookreader.fragments.multiplepageview.presenter.MultipageFragmentPresenter
 */

public class MultipageFragmentPresenterImpl implements MultipageFragmentPresenter {
    private MultipageFragmentView multipageFragmentView;

    public MultipageFragmentPresenterImpl(MultipageFragmentView multipageFragmentView) {
        this.multipageFragmentView = multipageFragmentView;
    }
}
