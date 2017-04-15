package com.dev.trident.comicbookreader.activities.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.about.AboutActivity;
import com.dev.trident.comicbookreader.activities.filechoose.FileChooseActivity;
import com.dev.trident.comicbookreader.activities.main.presenter.ComicsType;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenter;
import com.dev.trident.comicbookreader.activities.main.presenter.MainPresenterImpl;
import com.dev.trident.comicbookreader.activities.main.view.MainView;
import com.dev.trident.comicbookreader.activities.multipage.MultipageActivity;
import com.dev.trident.comicbookreader.activities.settings.SettingsActivity;
import com.dev.trident.comicbookreader.fragments.navigationtab.NavigationTabFragment;
import com.dev.trident.comicbookreader.fragments.navigationtab.view.NavigationTabFragmentView;
import com.dev.trident.comicbookreader.other.Utils;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity extends AppCompatActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener,
        NavigationTabFragment.OnFragmentInteractionListener {

    @Bind(R.id.clMainActivity)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.dlMainActivity)
    DrawerLayout drawerLayout;
    @Bind(R.id.nvMainActivity)
    NavigationView navigationView;
    @Bind(R.id.vpMainActivity)
    ViewPager viewPager;
    @Bind(R.id.fabNavigationMainActivity)
    FloatingActionButton fabNavigation;

    NavigationTabFragmentView navigationTabFragment;


    private MainPresenter mainPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onCreate()
     */
    @Override
    public void init(){
        ButterKnife.bind(this);
        setPresenter();
        requestReadFilesPermission();

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.tbMainActivity);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_left);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        navigationView.setNavigationItemSelectedListener(this);

        navigationTabFragment = NavigationTabFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frNavigationMainActivity, (Fragment) navigationTabFragment)
                .commit();
        mainPresenter.onViewReady(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_item_switch_mode:
                //Toast.makeText(this,"Switch mode",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MultipageActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()){
            case R.id.drawer_item_open_file:
                //Toast.makeText(this,"Open file",Toast.LENGTH_SHORT).show();
                if(Utils.doesUserHavePermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                    String[] comicsTypes = ComicsType.getAllTypes();
                    FilePickerBuilder.getInstance()
                            .setMaxCount(1)
                            .setActivityTheme(R.style.AppTheme)
                            .addFileSupport(getContext().getString(R.string.file_choose_comics_label),comicsTypes, R.drawable.ic_archive)
                            .enableDocSupport(false)
                            .setSelectedFiles(new ArrayList<String>())
                            .pickFile(MainActivity.this);
                } else requestReadFilesPermission();

                return true;
            case R.id.drawer_item_settings:
                //Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.drawer_item_about_this_app:
                //Toast.makeText(this,"About app",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            case R.id.drawer_item_exit:
                //Toast.makeText(this,"Exit",Toast.LENGTH_SHORT).show();
                showExitDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fabNavigationMainActivity)
    void onFabNavigationClicked(){
        Toast.makeText(this,"Navigation",Toast.LENGTH_SHORT).show();
    }

    private void showExitDialog(){
        showExitDialog(
            new Utils.BinaryActionListener() {
               @Override
               public void onActionChosen(DialogInterface dialogInterface, boolean isAllowed) {
                   if (isAllowed) {
                       MainActivity.this.finish();
                   }
                   dialogInterface.cancel();
               }
           }
        );
    }
    private void showExitDialog(Utils.BinaryActionListener listener){
        Utils.showBinaryActionDialog(this,
            R.string.dialog_button_exit,
            R.string.dialog_exit_message,
            R.string.dialog_button_exit,
            R.string.action_button_cancel,
            listener);
    }

    @Override
    public void setPresenter() {
        this.mainPresenter = new MainPresenterImpl();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {

            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    ArrayList<String> docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    Log.d(TAG,"path: "+docPaths.get(0));
                }
                break;
        }
    }

    void requestReadFilesPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MAIN_ACTIVITY_REQUEST_PERMISSION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, getContext().getString(R.string.permission_canceled_message), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }

}
