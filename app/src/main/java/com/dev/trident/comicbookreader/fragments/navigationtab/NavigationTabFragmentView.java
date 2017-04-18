package com.dev.trident.comicbookreader.fragments.navigationtab;

/**
 * trident 19/04/2017.
 */

public interface NavigationTabFragmentView {
    /**
     * Init with the count of pages
     * @param pageCount
     */
    void initWithPageCount(int pageCount);

    /**
     * Set current page
     * @param page
     */
    void setCurrentPage(int page);
}
