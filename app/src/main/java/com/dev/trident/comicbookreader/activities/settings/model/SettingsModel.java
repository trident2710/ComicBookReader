package com.dev.trident.comicbookreader.activities.settings.model;

import android.content.Context;

/**
 * trident 13/04/2017.
 *
 * Interface declaring the model layer functions for SettingsActivity
 *
 */


public interface SettingsModel {
    String PREFERENCE_PAGE_FILTERING = "com.dev.trident.comicbookreader.pagefiltering";
    /**
     * get current filtering saved in settings
     * @return current filtering saved in settings
     * @see SettingsFiltering
     */
    SettingsFiltering getCurrentFiltering(Context context);

    /**
     * save the filtering to the preferences
     */
    void saveFiltering(SettingsFiltering settingsFiltering,Context context);
}
