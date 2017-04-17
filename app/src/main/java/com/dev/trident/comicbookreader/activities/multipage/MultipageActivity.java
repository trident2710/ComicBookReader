package com.dev.trident.comicbookreader.activities.multipage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.dev.trident.comicbookreader.MVPBasic.MessageType;
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
    @Override
    public void init(){
        setPresenter();
        setSupportActionBar((Toolbar)findViewById(R.id.tbMultipageActivity));
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        multipagePresenter.onViewReady(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipage);
        init();
    }

    @Override
    public void setPresenter() {
        multipagePresenter = new MultipagePresenterImpl();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMessage(MessageType type, String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(MessageType type, int msgStringId) {
        Toast.makeText(this,getString(msgStringId),Toast.LENGTH_SHORT).show();
    }
}
