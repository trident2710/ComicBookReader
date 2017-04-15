package com.dev.trident.comicbookreader.fragments.singlepageview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.fragments.singlepageview.presenter.SinglepageFragmentPresenter;
import com.dev.trident.comicbookreader.fragments.singlepageview.presenter.SinglepageFragmentPresenterImpl;
import com.dev.trident.comicbookreader.fragments.singlepageview.view.SinglepageFragmentView;

/**
 */
public class SinglepageFragment extends Fragment implements SinglepageFragmentView{


    private OnFragmentInteractionListener mListener;

    private SinglepageFragmentPresenter singlepageFragmentPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onAttach()
     */
    @Override
    public void init(){
       setPresenter();
        singlepageFragmentPresenter.onViewReady(this);
    }

    public SinglepageFragment() {
        // Required empty public constructor
    }


    public static SinglepageFragment newInstance() {
        SinglepageFragment fragment = new SinglepageFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singlepage, container, false);
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
    public void setPresenter() {
        singlepageFragmentPresenter = new SinglepageFragmentPresenterImpl();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
