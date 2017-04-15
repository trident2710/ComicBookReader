package com.dev.trident.comicbookreader.activities.main.view;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the view layer functions for MainActivity

 */

public interface MainView extends BasicView{
    /**
     * Request permission (for api >25)
     */
    int MAIN_ACTIVITY_REQUEST_PERMISSION = 0x1;
    String TAG = "MainView";
}
