package com.dev.trident.comicbookreader.activities.main.presenter;

import com.dev.trident.comicbookreader.activities.main.model.MainModel;
import com.dev.trident.comicbookreader.activities.main.view.MainView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for MainActivity
 * @see com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter
 */

public class MainPresenterImpl implements MainPresenter {
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }
}
