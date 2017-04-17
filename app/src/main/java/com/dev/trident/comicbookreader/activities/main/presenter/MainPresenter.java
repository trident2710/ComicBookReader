package com.dev.trident.comicbookreader.activities.main.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicPresenter;

/**
 * trident 13/04/2017.
 * Interface declaring the presenter layer functions for MainActivity
 */

public interface MainPresenter extends BasicPresenter{
    /**
     * request to open the file by it's path
     * @param filePath - the path of this file
     */
    void requestOpenFile(String filePath);

    /**
     * request to open the page
     * @param pageNum - the number of page
     */
    void requestPage(int pageNum);
}
