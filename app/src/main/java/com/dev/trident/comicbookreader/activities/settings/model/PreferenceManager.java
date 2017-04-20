package com.dev.trident.comicbookreader.activities.settings.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * trident 20/04/2017.
 */

public final class PreferenceManager {
    String PREFERENCE_PAGE_FILTERING = "com.dev.trident.comicbookreader.pagefiltering";
    private static PreferenceManager _instance = new PreferenceManager();
    private PreferenceManager(){

    }
    public static PreferenceManager getInstance(){
        return _instance;
    }

    private SharedPreferences getSharedPreferences(Context ctx) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public void saveFiltering(Context ctx,SettingsFiltering setting){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREFERENCE_PAGE_FILTERING, setting.name());
        editor.apply();
    }

    /**
     * Get the filtering chosen by user in Settings
     * @see SettingsFiltering
     * @param ctx
     * @return
     */
    public SettingsFiltering getSavedFiltering(Context ctx){
        return getSharedPreferences(ctx).getString(PREFERENCE_PAGE_FILTERING,SettingsFiltering.IMAGE.name()).
                equals(SettingsFiltering.IMAGE.name())?SettingsFiltering.IMAGE:SettingsFiltering.TEXT;
    }



}
