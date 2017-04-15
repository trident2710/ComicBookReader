package com.dev.trident.comicbookreader.activities.settings.view;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsFiltering;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the view layer functions for SettingsActivity

 */

public interface SettingsView extends BasicView{

    /**
     * Sets the filtering option chosen on the view of SettingsActivity
     * @param filteringOption
     */
    void setFilteringOption(SettingsFiltering filteringOption);
}
