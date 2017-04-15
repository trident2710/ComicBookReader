package com.dev.trident.comicbookreader;

import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenter;
import com.dev.trident.comicbookreader.activities.settings.presenter.SettingsPresenterImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
