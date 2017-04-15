package com.dev.trident.comicbookreader.MVPBasic;

/**
 * trident 15/04/2017.
 * The super-interface for the presenter layer
 * Should be implemented by all presenter layer interfaces
 */

public interface BasicPresenter {
    /**
     * Should be invoked when the view layer is ready
     */
    void onViewReady(BasicView view);
}
