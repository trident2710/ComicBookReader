package com.dev.trident.comicbookreader.activities.main.view;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;

import java.io.InputStream;

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

    /**
     * show the opened comic book file
     * @param fileName - the name of the file
     * @param pageCount - the count of pages in the file
     */
    void showFile(String fileName,int pageCount);

    /**
     * show the comic book page
     * @param pageNum - the number of page
     * @param pageStream - the image input stream
     */
    void showPage(int pageNum, InputStream pageStream);
}
