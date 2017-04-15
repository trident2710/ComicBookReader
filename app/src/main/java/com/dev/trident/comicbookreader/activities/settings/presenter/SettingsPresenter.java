package com.dev.trident.comicbookreader.activities.settings.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicPresenter;

/**
 * trident 13/04/2017.
 * Interface declaring the presenter layer functions for SettingsActivity
 */

public interface SettingsPresenter extends BasicPresenter{

    /**
     * Invokes when user is changing the filtering setting
     * @param filteringChosen - one of SettingsFiltering options
     * @see SettingsFiltering
     */
    void onFilteringChanged(SettingsFiltering filteringChosen);
}
