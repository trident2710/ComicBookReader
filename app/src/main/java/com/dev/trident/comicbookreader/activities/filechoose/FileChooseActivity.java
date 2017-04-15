package com.dev.trident.comicbookreader.activities.filechoose;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dev.trident.comicbookreader.MVPBasic.BasicPresenter;
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
    @Override
    public void init(){
        setPresenter();
        setSupportActionBar((Toolbar)findViewById(R.id.tbFileChooseActivity));
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        fileChoosePresenter.onViewReady(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
        init();
    }

    @Override
    public void setPresenter() {
        fileChoosePresenter = new FileChoosePresenterImpl();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
