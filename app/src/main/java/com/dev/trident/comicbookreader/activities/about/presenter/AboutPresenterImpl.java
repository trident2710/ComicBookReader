package com.dev.trident.comicbookreader.activities.about.presenter;

import com.dev.trident.comicbookreader.activities.about.model.AboutModel;
import com.dev.trident.comicbookreader.activities.about.model.AboutModelImpl;
import com.dev.trident.comicbookreader.activities.about.view.AboutView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for AboutActivity
 * @see com.dev.trident.comicbookreader.activities.about.presenter.AboutPresenter
 */

public class AboutPresenterImpl implements AboutPresenter {
    /**
     * exemplar of AboutModel interface
     */
    private AboutModel aboutModel;
    /**
     * exemplar of AboutView interface
     */
    private AboutView aboutView;


    public AboutPresenterImpl(AboutView aboutView) {
        this.aboutView = aboutView;
        this.aboutModel = new AboutModelImpl();
    }
}
