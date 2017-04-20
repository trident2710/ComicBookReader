package com.dev.trident.comicbookreader.fragments.reader;

/**
 * trident 19/04/2017.
 * The interface to communicate with the fragments which display the pages of the comic book
 * @see MultipageFragment
 * @see SinglepageFragment
 */

public interface ReaderFragmentView {
    /**
     *
     * @return the count of pages in current comic book
     */
    int getPageCount();

    /**
     *
     * @return the currently displayed page
     */
    int getCurrentPage();

    /**
     * Go to the specific page
     * @param page number of page
     */
    void moveToPage(int page);

    /**
     *
     * @return the path of the currently opened file
     */
    String getFilePath();

    /**
     * reload the pages
     */
    void reloadPages();
}
