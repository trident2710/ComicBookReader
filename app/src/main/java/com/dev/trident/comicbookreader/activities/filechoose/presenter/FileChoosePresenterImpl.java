package com.dev.trident.comicbookreader.activities.filechoose.presenter;

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

    public FileChoosePresenterImpl(FileChooseView fileChooseView) {
        this.fileChooseView = fileChooseView;
        this.fileChooseModel = new FileChooseModelImpl();
    }
}
