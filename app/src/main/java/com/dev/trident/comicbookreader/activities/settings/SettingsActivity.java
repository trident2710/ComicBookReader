package com.dev.trident.comicbookreader.activities.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenter;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenterImpl;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsFiltering;
import com.dev.trident.comicbookreader.activities.settings.view.SettingsView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements SettingsView, RadioGroup.OnCheckedChangeListener {

    private SettingsPresenter settingsPresenter;

    @Bind(R.id.rgFilteringSettingsActivity)
    RadioGroup radioGroupFiltering;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    @Override
    public void init(){
        setPresenter();
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar)findViewById(R.id.tbSettingsActivity));
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        radioGroupFiltering.setOnCheckedChangeListener(this);
        settingsPresenter.onViewReady(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        this.settingsPresenter.onFilteringChanged(
                checkedId==R.id.rbFilteringTextSettingsActivity?
                        SettingsFiltering.TEXT:
                        SettingsFiltering.IMAGE);
    }

    @Override
    public void setFilteringOption(SettingsFiltering filteringOption) {
        ((RadioButton)this.radioGroupFiltering.getChildAt(filteringOption==SettingsFiltering.TEXT?0:1)).setChecked(true);
    }

    @Override
    public void setPresenter() {
        settingsPresenter = new SettingsPresenterImpl();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
