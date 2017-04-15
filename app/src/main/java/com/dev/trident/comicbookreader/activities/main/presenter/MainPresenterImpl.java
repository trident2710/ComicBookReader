package com.dev.trident.comicbookreader.activities.main.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.activities.filechoose.view.FileChooseView;
import com.dev.trident.comicbookreader.activities.main.model.MainModel;
import com.dev.trident.comicbookreader.activities.main.model.MainModelImpl;
import com.dev.trident.comicbookreader.activities.main.view.MainView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for MainActivity
 * @see com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter
 */

public class MainPresenterImpl implements MainPresenter {
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenterImpl() {
        this.mainModel = new MainModelImpl();
    }

    @Override
    public void onViewReady(BasicView view) {
        try {
            mainView= (MainView) view;
        } catch (Exception ex){
            throw new RuntimeException("view must implement MainView");
        }
    }
}
