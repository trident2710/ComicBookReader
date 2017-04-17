package com.dev.trident.comicbookreader.activities.main.presenter;

import com.dev.trident.comicbookreader.MVPBasic.BasicView;
import com.dev.trident.comicbookreader.MVPBasic.MessageType;
import com.dev.trident.comicbookreader.activities.main.model.ComicFileCallback;
import com.dev.trident.comicbookreader.activities.main.model.MainModel;
import com.dev.trident.comicbookreader.activities.main.model.MainModelImpl;
import com.dev.trident.comicbookreader.activities.main.view.MainView;

import java.io.InputStream;

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

    @Override
    public void requestOpenFile(String filePath) {
        mainModel.openFile(filePath,comicFileCallback);
    }

    @Override
    public void requestPage(int pageNum) {
        mainModel.getPage(pageNum,comicFileCallback);
    }


    private final ComicFileCallback comicFileCallback = new ComicFileCallback() {
        @Override
        public void onFileOpened(String fileName, int pageCount) {
            mainView.showFile(fileName,pageCount);
        }

        @Override
        public void onPage(int pageNum, InputStream pageInputStream) {
            mainView.showPage(pageNum,pageInputStream);
        }

        @Override
        public void onPageReadError(Exception ex) {
            if(ex.getMessage()!=null&&ex.getMessage().length()!=0)
                mainView.showMessage(MessageType.ERROR,ex.getMessage());
            ex.printStackTrace();
        }

        @Override
        public void onFileOpenError(Exception ex) {
            if(ex.getMessage()!=null&&ex.getMessage().length()!=0)
                mainView.showMessage(MessageType.ERROR,ex.getMessage());
            ex.printStackTrace();
        }
    };
}
