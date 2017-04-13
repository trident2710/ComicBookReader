package com.dev.trident.comicbookreader.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenterImpl;
import com.dev.trident.comicbookreader.activities.main.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView{

    private MainPresenter mainPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        mainPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
