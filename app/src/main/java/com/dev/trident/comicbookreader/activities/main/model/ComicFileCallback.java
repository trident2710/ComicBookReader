package com.dev.trident.comicbookreader.activities.main.model;

import java.io.InputStream;

/**
 * Callback for comic book file opening process
 * @see MainModel
 * @see com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter
 * trident 17/04/2017.
 */

public interface ComicFileCallback {
    /**
     * invoked when the file is opened successfully
     * @param fileName - the name of the opened file
     * @param pageCount - the count of pages in the file
     */
    void onFileOpened(String fileName, int pageCount);

    /**
     * Invoked when the page was read successfully
     * @param pageInputStream - the input stream of the page
     * @param pageNum - the number of page
     */
    void onPage(int pageNum, InputStream pageInputStream);

    /**
     * Invoked if page was opened with error
     * @param ex - exception occurred
     */
    void onPageReadError(Exception ex);

    /**
     * Invoked if file was opened with error
     * @param ex - exception occurred
     */
    void onFileOpenError(Exception ex);

}
