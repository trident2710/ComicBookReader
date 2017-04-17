package com.dev.trident.comicbookreader;

import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenterImpl;

import org.junit.Test;

/**
 * trident 15/04/2017.
 */

public class SettingsPresenterImplTest {
    SettingsPresenterImpl settingsPresenter;
    @Test
    public void createTest() throws Exception{
        settingsPresenter = new SettingsPresenterImpl();
    }
}
