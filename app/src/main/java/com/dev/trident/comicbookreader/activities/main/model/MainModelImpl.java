package com.dev.trident.comicbookreader.activities.main.model;

import com.dev.trident.comicbookreader.other.archive.ArchiveReaderFactory;
import com.dev.trident.comicbookreader.other.archive.ComicsReader;

/**
 * trident 13/04/2017.
 * The realisation of interface declaring the model layer functions for MainActivity
 * @see com.dev.trident.comicbookreader.activities.main.model.MainModel
 */

public class MainModelImpl implements MainModel {
    private ComicsReader comicsReader;
    public MainModelImpl() {
    }

    @Override
    public void openFile(String filePath, ComicFileCallback callback) {
        try {
            comicsReader = ArchiveReaderFactory.getReader(filePath);
            callback.onFileOpened(comicsReader.getFileName(),comicsReader.getPageCount());
        } catch (Exception ex){
            callback.onFileOpenError(ex);
        }

    }

    @Override
    public void getPage(int pageNum,ComicFileCallback comicFileCallback) {
        if(comicsReader == null){
            comicFileCallback.onPageReadError(new Exception("the file was not opened"));
            return;
        }
        if(pageNum>=comicsReader.getPageCount()){
            comicFileCallback.onPageReadError(new Exception("the number of pages is greater than the page count"));
            return;
        }
        try {
            comicFileCallback.onPage(pageNum,comicsReader.getPageInputStream(pageNum));
        } catch (Exception ex){
            comicFileCallback.onPageReadError(ex);
        }
    }
}
