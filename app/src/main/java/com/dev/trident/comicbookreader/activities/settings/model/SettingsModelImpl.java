package com.dev.trident.comicbookreader.activities.settings.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsFiltering;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the model layer functions for SettingsActivity
 * @see com.dev.trident.comicbookreader.activities.settings.model.SettingsModel
 */

public class SettingsModelImpl implements SettingsModel {
    public SettingsModelImpl() {
    }

    private SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    private void saveFiltering(Context ctx,SettingsFiltering setting){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREFERENCE_PAGE_FILTERING, setting.name());
        editor.apply();
    }
    private SettingsFiltering getSavedFiltering(Context ctx){
        return getSharedPreferences(ctx).getString(PREFERENCE_PAGE_FILTERING,SettingsFiltering.IMAGE.name()).
                equals(SettingsFiltering.IMAGE.name())?SettingsFiltering.IMAGE:SettingsFiltering.TEXT;
    }

    @Override
    public SettingsFiltering getCurrentFiltering(Context context) {
        return getSavedFiltering(context);
    }

    @Override
    public void saveFiltering(SettingsFiltering settingsFiltering, Context context) {
        saveFiltering(context,settingsFiltering);
    }
}
