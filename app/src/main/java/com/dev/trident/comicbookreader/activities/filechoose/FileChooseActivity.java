package com.dev.trident.comicbookreader.activities.filechoose;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.filechoose.presenter.FileChoosePresenter;
import com.dev.trident.comicbookreader.activities.filechoose.presenter.FileChoosePresenterImpl;
import com.dev.trident.comicbookreader.activities.filechoose.view.FileChooseView;

public class FileChooseActivity extends AppCompatActivity implements FileChooseView{

    private FileChoosePresenter fileChoosePresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        fileChoosePresenter = new FileChoosePresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
        init();
    }
}
