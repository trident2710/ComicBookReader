package com.dev.trident.comicbookreader.fragments.reader;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.activities.settings.model.PreferenceManager;
import com.dev.trident.comicbookreader.activities.settings.model.SettingsFiltering;
import com.dev.trident.comicbookreader.fragments.navigationtab.NavigationTabFragment;
import com.dev.trident.comicbookreader.other.Constants;
import com.dev.trident.comicbookreader.other.Utils;
import com.dev.trident.comicbookreader.other.archive.ArchiveReaderFactory;
import com.dev.trident.comicbookreader.other.archive.CBRReader;
import com.dev.trident.comicbookreader.other.archive.ComicsReader;
import com.dev.trident.comicbookreader.other.view.ComicViewPager;
import com.dev.trident.comicbookreader.other.view.PageImageView;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.lang.ref.WeakReference;


public class SinglepageFragment extends Fragment implements View.OnTouchListener,ReaderFragmentView{

    public static final String PARAM_FILE_PATH = "ReaderFragment.FilePath";

    private ComicViewPager mViewPager;
    private ComicPagerAdapter mPagerAdapter;
    private SharedPreferences mPreferences;
    private GestureDetector mGestureDetector;


    private boolean mIsFullscreen;
    private int mCurrentPage;
    private String mFilename;
    private Constants.PageViewMode mPageViewMode;
    private boolean mIsLeftToRight;

    private ComicsReader mParser;
    private Picasso mPicasso;
    private LocalComicHandler mComicHandler;
    private SparseArray<Target> mTargets = new SparseArray<>();


    String filePath;

    private OnFragmentInteractionListener mListener;



    public static SinglepageFragment create(String comicpath) {
        SinglepageFragment fragment = new SinglepageFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_FILE_PATH, comicpath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        File file = null;
        String path =  bundle.getString(PARAM_FILE_PATH);
        filePath = path;
        file = new File(path);

        try {
            mParser = ArchiveReaderFactory.getReader(file.getPath());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        mFilename = file.getName();

        mCurrentPage = Math.max(1, Math.min(mCurrentPage, mParser.getPageCount()));

        mComicHandler = new LocalComicHandler(mParser);
        mPicasso = new Picasso.Builder(getActivity())
                .memoryCache(new LruCache(250000000))
                .addRequestHandler(mComicHandler)
                .build();
        mPagerAdapter = new ComicPagerAdapter();
        mGestureDetector = new GestureDetector(getActivity(), new MyTouchListener());

        mPreferences = getActivity().getSharedPreferences(Constants.SETTINGS_NAME, 0);
        int viewModeInt = mPreferences.getInt(
                Constants.SETTINGS_PAGE_VIEW_MODE,
                Constants.PageViewMode.ASPECT_FIT.native_int);
        mPageViewMode = Constants.PageViewMode.values()[viewModeInt];
        mIsLeftToRight = mPreferences.getBoolean(Constants.SETTINGS_READING_LEFT_TO_RIGHT, true);

        // workaround: extract rar achive
        if (mParser instanceof CBRReader) {
            File cacheDir = new File(getActivity().getExternalCacheDir(), "c");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            else {
                for (File f : cacheDir.listFiles()) {
                    f.delete();
                }
            }
            ((CBRReader)mParser).setCacheDirectory(cacheDir);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reader, container, false);



        mViewPager = (ComicViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (mIsLeftToRight) {
                    setCurrentPage(position + 1);
                    mListener.onSinglePageModePageSelected(position + 1);
                }
                else {
                    setCurrentPage(mViewPager.getAdapter().getCount() - position);
                    mListener.onSinglePageModePageSelected(mViewPager.getAdapter().getCount() - position);
                }

            }
        });


        if (mCurrentPage != -1) {
            setCurrentPage(mCurrentPage);
            mCurrentPage = -1;
        }

        setFullscreen(false);

        getActivity().setTitle(mFilename);


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        try {
            mParser.destroy();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        mPicasso.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public int getCurrentPage() {
        if (mIsLeftToRight)
            return mViewPager.getCurrentItem() + 1;
        else
            return mViewPager.getAdapter().getCount() - mViewPager.getCurrentItem();
    }

    @Override
    public void moveToPage(int page) {
        setCurrentPage(page);
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void reloadPages() {
        mViewPager.setAdapter(null);
        mViewPager.setAdapter(mPagerAdapter);
    }


    private void setCurrentPage(int page) {
        setCurrentPage(page, true);
    }

    private void setCurrentPage(int page, boolean animated) {
        if (mIsLeftToRight) {
            mViewPager.setCurrentItem(page - 1);

        }
        else {
            mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - page, animated);

        }


    }

    @Override
    public int getPageCount() {
        return mParser.getPageCount();
    }

    private class ComicPagerAdapter extends PagerAdapter {
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mParser.getPageCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final LayoutInflater inflater = (LayoutInflater)getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.fragment_reader_page, container, false);

            PageImageView pageImageView = (PageImageView) layout.findViewById(R.id.pageImageView);
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView)layout.findViewById(R.id.pageImageViewBetterQuality);
            if (mPageViewMode == Constants.PageViewMode.ASPECT_FILL)
                pageImageView.setTranslateToRightEdge(!mIsLeftToRight);
            pageImageView.setViewMode(mPageViewMode);
            pageImageView.setOnTouchListener(SinglepageFragment.this);
            subsamplingScaleImageView.setOnTouchListener(SinglepageFragment.this);

            container.addView(layout);

            MyTarget t = new MyTarget(layout, position);
            loadImage(t);
            mTargets.put(position, t);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View layout = (View) object;
            mPicasso.cancelRequest(mTargets.get(position));
            mTargets.delete(position);
            container.removeView(layout);

            ImageView iv = (ImageView) layout.findViewById(R.id.pageImageView);
            Drawable drawable = iv.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bm = bd.getBitmap();
                if (bm != null) {
                    bm.recycle();
                }
            }
        }
    }

    private void loadImage(MyTarget t) {
        int pos;
        if (mIsLeftToRight) {
            pos = t.position;
        }
        else {
            pos = mViewPager.getAdapter().getCount() - t.position - 1;
        }

        mPicasso.load(mComicHandler.getPageUri(pos))
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .tag(getActivity())
                .resize(Constants.MAX_PAGE_WIDTH, Constants.MAX_PAGE_HEIGHT)
                .centerInside()
                .onlyScaleDown()
                .into(t);
    }

    private boolean isPreferenceTextQuality(){
        return PreferenceManager.getInstance().getSavedFiltering(getActivity())== SettingsFiltering.TEXT;
    }

    private class MyTarget implements Target, View.OnClickListener {
        private WeakReference<View> mLayout;
        public final int position;

        public MyTarget(View layout, int position) {
            mLayout = new WeakReference<>(layout);
            this.position = position;
        }

        private void setVisibility(int imageView, int progressBar, int reloadButton) {
            View layout = mLayout.get();
            if(isPreferenceTextQuality())
                layout.findViewById(R.id.pageImageViewBetterQuality).setVisibility(imageView);
            else
                layout.findViewById(R.id.pageImageView).setVisibility(imageView);

            layout.findViewById(R.id.pageProgressBar).setVisibility(progressBar);
            layout.findViewById(R.id.reloadButton).setVisibility(reloadButton);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            View layout = mLayout.get();
            if (layout == null)
                return;

            setVisibility(View.VISIBLE, View.GONE, View.GONE);
            if(isPreferenceTextQuality()){
                SubsamplingScaleImageView iv = (SubsamplingScaleImageView) layout.findViewById(R.id.pageImageViewBetterQuality);
                iv.setImage(ImageSource.bitmap(bitmap));
            } else {
                ImageView iv = (ImageView) layout.findViewById(R.id.pageImageView);
                iv.setImageBitmap(bitmap);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            View layout = mLayout.get();
            if (layout == null)
                return;

            setVisibility(View.GONE, View.GONE, View.VISIBLE);

            ImageButton ib = (ImageButton) layout.findViewById(R.id.reloadButton);
            ib.setOnClickListener(this);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

        @Override
        public void onClick(View v) {
            View layout = mLayout.get();
            if (layout == null)
                return;

            setVisibility(View.GONE, View.VISIBLE, View.GONE);
            loadImage(this);
        }
    }

    private class MyTouchListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!isFullscreen()) {
                setFullscreen(true, true);
                return true;
            }

            float x = e.getX();

            // tap left edge
            if (x < (float) mViewPager.getWidth() / 3) {
                if (mIsLeftToRight) {
                    if (getCurrentPage() >= 1)
                        setCurrentPage(getCurrentPage() - 1);
                }
                else {
                    if (getCurrentPage() <= mViewPager.getAdapter().getCount())
                        setCurrentPage(getCurrentPage() + 1);
                }
            }
            // tap right edge
            else if (x > (float) mViewPager.getWidth() / 3 * 2) {
                if (mIsLeftToRight) {
                    if (getCurrentPage() <= mViewPager.getAdapter().getCount())
                        setCurrentPage(getCurrentPage() + 1);
                }
                else {
                    if (getCurrentPage() >= 1)
                        setCurrentPage(getCurrentPage() - 1);
                }
            }
            else
                setFullscreen(false, true);

            return true;
        }
    }


    private ActionBar getActionBar() {
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    private void setFullscreen(boolean fullscreen) {
        mListener.onFullscreenMode(fullscreen);
        setFullscreen(fullscreen, false);
    }

    private void setFullscreen(boolean fullscreen, boolean animated) {
        mIsFullscreen = fullscreen;

        ActionBar actionBar = getActionBar();

        if (fullscreen) {
            if (actionBar != null) actionBar.hide();

            int flag =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (Utils.isKitKatOrLater()) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                flag |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            mViewPager.setSystemUiVisibility(flag);

           // mPageNavLayout.setVisibility(View.INVISIBLE);
        }
        else {
            if (actionBar != null) actionBar.show();

            int flag =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Utils.isKitKatOrLater()) {
                flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            }
            mViewPager.setSystemUiVisibility(flag);

           // mPageNavLayout.setVisibility(View.VISIBLE);

            // status bar & navigation bar background won't show in some cases
            if (Utils.isLollipopOrLater()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Window w = getActivity().getWindow();
                        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    }
                }, 300);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationTabFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean isFullscreen() {
        return mIsFullscreen;
    }


    public interface OnFragmentInteractionListener {
        /**
         * notify the host activity that this view has entered/exited the fullscreen mode
         * @param fullscreenMode - indicate the fullscreen state
         */
        void onFullscreenMode(boolean fullscreenMode);

        void onSinglePageModePageSelected(int page);
    }




}
