package com.dev.trident.comicbookreader.fragments.multiplepageview.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.fragments.multiplepageview.view.MultipageFragmentView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for MultipageFragment
 * @see com.dev.trident.comicbookreader.fragments.multiplepageview.presenter.MultipageFragmentPresenter
 */

public class MultipageFragmentPresenterImpl implements MultipageFragmentPresenter {
    private MultipageFragmentView multipageFragmentView;

    public MultipageFragmentPresenterImpl() {
    }

    @Override
    public void onViewReady(BasicView view) {
        try {
            multipageFragmentView = (MultipageFragmentView) view;
        } catch (Exception ex){
            throw new RuntimeException("view must implement MultipageFragmentView");
        }
    }
}
