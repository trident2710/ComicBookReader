package com.dev.trident.comicbookreader.activities.filechoose.presenter;

import android.util.Log;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.activities.filechoose.model.FileChooseModel;
import com.dev.trident.comicbookreader.activities.filechoose.model.FileChooseModelImpl;
import com.dev.trident.comicbookreader.activities.filechoose.view.FileChooseView;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the presenter layer functions for FileChooseActivity
 * @see com.dev.trident.comicbookreader.activities.filechoose.presenter.FileChoosePresenter
 */

public class FileChoosePresenterImpl implements FileChoosePresenter {
    private FileChooseModel fileChooseModel;
    private FileChooseView fileChooseView;

    public FileChoosePresenterImpl() {
        this.fileChooseModel = new FileChooseModelImpl();
    }

    @Override
    public void onViewReady(BasicView view) {
         try {
             fileChooseView = (FileChooseView)view;
         } catch (Exception ex){
             throw new RuntimeException("view must implement FileChooseView");
         }
    }

}
