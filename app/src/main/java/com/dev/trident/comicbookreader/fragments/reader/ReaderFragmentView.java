package com.dev.trident.comicbookreader.fragments.reader;

/**
 * trident 19/04/2017.
 */

public interface ReaderFragmentView {
    int getPageCount();
    int getCurrentPage();
    void moveToPage(int page);
    String getFilePath();
    void reloadPages();
}
