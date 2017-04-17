package com.dev.trident.comicbookreader.activities.main.model;

/**
 * trident 13/04/2017.
 *
 * Interface declaring the model layer functions for MainActivity
 *
 */

public interface MainModel {
    /**
     * open the file from it's path
     * @param filePath - the path to the file
     * @param callback - the response callback
     */
    void openFile(String filePath,ComicFileCallback callback);

    /**
     * get the page from comics file by page number
     * @param pageNum - page number
     * @param callback - the response callback
     */
    void getPage(int pageNum,ComicFileCallback callback);
}
