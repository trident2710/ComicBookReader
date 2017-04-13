package com.dev.trident.comicbookreader.activities.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.about.presenter.AboutPresenter;
import com.dev.trident.comicbookreader.activities.about.presenter.AboutPresenterImpl;
import com.dev.trident.comicbookreader.activities.about.view.AboutView;

public class AboutActivity extends AppCompatActivity implements AboutView{

    private AboutPresenter aboutPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        aboutPresenter = new AboutPresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }


}
