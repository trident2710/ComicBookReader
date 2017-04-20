package com.dev.trident.comicbookreader.activities.settings.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.activities.settings.model.SettingsFiltering;
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

    public SettingsPresenterImpl() {
        this.settingsModel = new SettingsModelImpl();
    }

    @Override
    public void onFilteringChanged(SettingsFiltering filteringChosen) {
        settingsModel.saveFiltering(filteringChosen,settingsView.getContext());
    }


    @Override
    public void onViewReady(BasicView view) {
        try {
            settingsView = (SettingsView) view;
        } catch (Exception ex){
            throw new RuntimeException("view must implement SettingsView");
        }
        settingsView.setFilteringOption(settingsModel.getCurrentFiltering(settingsView.getContext()));
    }
}
