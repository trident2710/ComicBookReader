package com.dev.trident.comicbookreader.activities.settings.model;

import android.content.Context;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the model layer functions for SettingsActivity
 * @see com.dev.trident.comicbookreader.activities.settings.model.SettingsModel
 */

public class SettingsModelImpl implements SettingsModel {
    public SettingsModelImpl() {
    }



    @Override
    public SettingsFiltering getCurrentFiltering(Context context) {
        return PreferenceManager.getInstance().getSavedFiltering(context);
    }

    @Override
    public void saveFiltering(SettingsFiltering settingsFiltering, Context context) {
        PreferenceManager.getInstance().saveFiltering(context,settingsFiltering);
    }
}
