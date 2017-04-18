package com.dev.trident.comicbookreader.fragments.navigationtab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.trident.comicbookreader.R;
import com.devadvance.circularseekbar.CircularSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**

 */
public class NavigationTabFragment extends Fragment implements NavigationTabFragmentView,CircularSeekBar.OnCircularSeekBarChangeListener{

    private OnFragmentInteractionListener mListener;
    private static final int REFRESH_TIME_MILLIS = 100;

    @Bind(R.id.circularSeekBar)
    CircularSeekBar circularSeekBar;
    @Bind(R.id.etSetPage)
    EditText etSetPage;
    @Bind(R.id.tvMaxPages)
    TextView tvMaxPages;

    private int pageCount = 0;
    private int currentPage = 1;


    /**
     * Initialisation of key components of this activity
     * Should be called in onAttach()
     */
    public void init() {
    }

    public NavigationTabFragment() {
        // Required empty public constructor
    }


    public static NavigationTabFragment newInstance() {
        NavigationTabFragment fragment = new NavigationTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_page,null);
        ButterKnife.bind(this,v);
        circularSeekBar.setOnSeekBarChangeListener(this);
        etSetPage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s){
                try {
                    int val = Integer.parseInt(s.toString());
                    if(val<=0) {
                        setCurrentPage(1);
                    };
                    if(val>pageCount&&pageCount>0){
                        setCurrentPage(pageCount);
                    }
                } catch (Exception ex){
                    setCurrentPage(1);
                }
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        init();
        if (context instanceof OnFragmentInteractionListener) {
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


    @Override
    public void onResume(){
        super.onResume();
        //listener.onPageDataRequested();
    }

    @Override
    public void initWithPageCount(int pageCount) {
        this.pageCount = pageCount;
        if (tvMaxPages!=null) tvMaxPages.setText("/"+pageCount);
        if(circularSeekBar!=null) circularSeekBar.setMax(pageCount);
        if(circularSeekBar!=null) circularSeekBar.setProgress(0);
    }

    @Override
    public void setCurrentPage(int page) {
        this.currentPage = page;
        if (etSetPage!=null) etSetPage.setText(""+currentPage);
        if (circularSeekBar!=null) circularSeekBar.setProgress(currentPage);
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
    }

    Handler progressListener = new Handler();

    Runnable progressRunnable  = new Runnable() {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentPage = circularSeekBar.getProgress();
                    etSetPage.setText(""+currentPage);
                }

            });
            progressListener.postDelayed(NavigationTabFragment.this.progressRunnable,REFRESH_TIME_MILLIS);
        }
    };

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {
        progressListener.removeCallbacks(progressRunnable);
        mListener.onPageSelected(currentPage);
    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {
        progressListener.post(progressRunnable);
    }


    public interface OnFragmentInteractionListener {
        /**
         * notify the listener that the page was selected for display
         * @param pageNum - number of page
         */
        void onPageSelected(int pageNum);
    }

}
