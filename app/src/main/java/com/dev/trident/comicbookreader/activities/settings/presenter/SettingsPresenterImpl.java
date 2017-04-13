package com.dev.trident.comicbookreader.activities.settings.presenter;

import com.dev.trident.comicbookreader.activities.settings.model.SettingsModel;
import com.dev.trident.comicbookreader.activities.settings.model.SettingsModelImpl;
import com.dev.trident.comicbookreader.activities.settings.view.SettingsView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for SettingsActivity
 * @see com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenter
 */

public class SettingsPresenterImpl implements SettingsPresenter {
    private SettingsModel settingsModel;
    private SettingsView settingsView;

    public SettingsPresenterImpl(SettingsView settingsView) {
        this.settingsView = settingsView;
        this.settingsModel = new SettingsModelImpl();
    }
}
