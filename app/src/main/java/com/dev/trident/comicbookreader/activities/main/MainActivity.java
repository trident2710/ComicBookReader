package com.dev.trident.comicbookreader.activities.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenterImpl;
import com.dev.trident.comicbookreader.activities.main.view.MainView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.clMainActivity)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.dlMainActivity)
    DrawerLayout drawerLayout;
    @Bind(R.id.nvMainActivity)
    NavigationView navigationView;

    private MainPresenter mainPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    void init(){
        ButterKnife.bind(this);
        mainPresenter = new MainPresenterImpl(this);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.tbMainActivity);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_left);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
