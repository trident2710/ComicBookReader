package com.dev.trident.comicbookreader.activities.multipage.presenter;

import com.dev.trident.comicbookreader.activities.multipage.model.MultipageModel;
import com.dev.trident.comicbookreader.activities.multipage.model.MultipageModelImpl;
import com.dev.trident.comicbookreader.activities.multipage.view.MultipageView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for MultipageActivity
 * @see com.dev.trident.comicbookreader.activities.multipage.presenter.MultipagePresenter
 */

public class MultipagePresenterImpl implements MultipagePresenter {
    private MultipageModel multipageModel;
    private MultipageView multipageView;

    public MultipagePresenterImpl(MultipageView multipageView) {
        this.multipageView = multipageView;
        this.multipageModel = new MultipageModelImpl();
    }
}