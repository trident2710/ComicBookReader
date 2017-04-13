package com.dev.trident.comicbookreader.activities.multipage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.multipage.presenter.MultipagePresenter;
import com.dev.trident.comicbookreader.activities.multipage.presenter.MultipagePresenterImpl;
import com.dev.trident.comicbookreader.activities.multipage.view.MultipageView;

public class MultipageActivity extends AppCompatActivity implements MultipageView{

    private MultipagePresenter multipagePresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        multipagePresenter = new MultipagePresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipage);
        init();
    }
}
