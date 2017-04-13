package com.dev.trident.comicbookreader.fragments.navigationtab.presenter;

import com.dev.trident.comicbookreader.fragments.navigationtab.model.NavigationTabFragmentModel;
import com.dev.trident.comicbookreader.fragments.navigationtab.model.NavigationTabFragmentModelImpl;
import com.dev.trident.comicbookreader.fragments.navigationtab.view.NavigationTabFragmentView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for NavigationTabFragment
 * @see com.dev.trident.comicbookreader.fragments.navigationtab.presenter.NavigationTabFragmentPresenter
 */

public class NavigationTabFragmentPresenterImpl implements NavigationTabFragmentPresenter {
    private NavigationTabFragmentModel navigationTabFragmentModel;
    private NavigationTabFragmentView navigationTabFragmentView;

    public NavigationTabFragmentPresenterImpl(NavigationTabFragmentView navigationTabFragmentView) {
        this.navigationTabFragmentView = navigationTabFragmentView;
        this.navigationTabFragmentModel = new NavigationTabFragmentModelImpl();
    }
}
