package com.dev.trident.comicbookreader.MVPBasic;

import android.content.Context;

/**
 * trident 15/04/2017.
 * The super-interface for the view layer
 * Should be implemented by all view layer interfaces
 */

public interface BasicView {
    /**
     * All local components should be initialised in this method
     */
    void init();
    /**
     * should be invoked during the view component initialisation
     */
    void setPresenter();

    /**
     * return the context of this view
     */
    Context getContext();

    /**
     * display the message to user on the screen
     * @param msg - error message
     * @param type -message type
     * @see MessageType
     */
    void showMessage(MessageType type, String msg);

    /**
     * display the message to user on the screen
     * @param msgStringId - error message string id
     * @param type -message type
     * @see MessageType
     */
    void showMessage(MessageType type, int msgStringId);
}
