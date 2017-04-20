package com.dev.trident.comicbookreader.fragments.reader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.fragments.navigationtab.NavigationTabFragment;
import com.dev.trident.comicbookreader.other.Constants;
import com.dev.trident.comicbookreader.other.archive.ArchiveReaderFactory;
import com.dev.trident.comicbookreader.other.archive.CBRReader;
import com.dev.trident.comicbookreader.other.archive.ComicsReader;
import com.pixplicity.multiviewpager.MultiViewPager;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.lang.ref.WeakReference;


public class MultipageFragment extends Fragment implements ReaderFragmentView{

    public static final String PARAM_FILE_PATH = "MultipageFragment.FilePath";

    private MultiViewPager mViewPager;
    private ComicPagerAdapter mPagerAdapter;


    private int mCurrentPage;

    private ComicsReader mParser;
    private Picasso mPicasso;
    private LocalComicHandler mComicHandler;
    private SparseArray<Target> mTargets = new SparseArray<>();
    String filePath;

    private OnFragmentInteractionListener mListener;

    public static MultipageFragment create(String comicpath) {
        MultipageFragment fragment = new MultipageFragment();
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

        mCurrentPage = Math.max(1, Math.min(mCurrentPage, mParser.getPageCount()));

        mComicHandler = new LocalComicHandler(mParser);
        mPicasso = new Picasso.Builder(getActivity())
                .memoryCache(new LruCache(250000000))
                .addRequestHandler(mComicHandler)
                .build();
        mPagerAdapter = new ComicPagerAdapter();

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_multipage, container, false);



        mViewPager = (MultiViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                    setCurrentPage(mViewPager.getAdapter().getCount() - position);
                mListener.onMultiPageModePageSelected(mViewPager.getAdapter().getCount() - position);
            }
        });


        if (mCurrentPage != -1) {
            setCurrentPage(mCurrentPage);
            mCurrentPage = -1;
        }

        return view;
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
    public int getCurrentPage() {
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
        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - page, animated);
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

            View layout = inflater.inflate(R.layout.fragment_multipage_item, container, false);
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

//            ImageView iv = (ImageView) layout.findViewById(R.id.pageImageView);
//            Drawable drawable = iv.getDrawable();
//            if (drawable instanceof BitmapDrawable) {
//                BitmapDrawable bd = (BitmapDrawable) drawable;
//                Bitmap bm = bd.getBitmap();
//                if (bm != null) {
//                    bm.recycle();
//                }
//            }
        }
    }

    private void loadImage(MyTarget t) {
        int pos;
        pos = mViewPager.getAdapter().getCount() - t.position - 1;

        mPicasso.load(mComicHandler.getPageUri(pos))
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .tag(getActivity())
                .resize(Constants.MAX_PAGE_WIDTH, Constants.MAX_PAGE_HEIGHT)
                .centerInside()
                .onlyScaleDown()
                .into(t);
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
            SubsamplingScaleImageView iv = (SubsamplingScaleImageView) layout.findViewById(R.id.pageImageView);
            iv.setImage(ImageSource.bitmap(bitmap));
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



    public interface OnFragmentInteractionListener {
        void onMultiPageModePageSelected(int page);
    }




}
