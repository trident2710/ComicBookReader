package com.dev.trident.comicbookreader.activities.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenter;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenterImpl;
import com.dev.trident.comicbookreader.activities.settings.view.SettingsView;

public class SettingsActivity extends AppCompatActivity implements SettingsView{

    private SettingsPresenter settingsPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        settingsPresenter = new SettingsPresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }
}
