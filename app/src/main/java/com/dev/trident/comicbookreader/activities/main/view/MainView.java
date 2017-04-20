package com.dev.trident.comicbookreader.activities.main.view;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the view layer functions for MainActivity

 */

public interface MainView{
    /**
     * Request permission (for api >25)
     */
    int MAIN_ACTIVITY_REQUEST_PERMISSION = 0x1;
    String TAG = "MainView";
    String ARG_FILE_PATH = "MainActivityFilePath";

}
