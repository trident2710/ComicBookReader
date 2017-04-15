package com.dev.trident.comicbookreader.fragments.navigationtab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.fragments.navigationtab.presenter.NavigationTabFragmentPresenter;
import com.dev.trident.comicbookreader.fragments.navigationtab.presenter.NavigationTabFragmentPresenterImpl;
import com.dev.trident.comicbookreader.fragments.navigationtab.view.NavigationTabFragmentView;

/**

 */
public class NavigationTabFragment extends Fragment implements NavigationTabFragmentView{

    private OnFragmentInteractionListener mListener;

    private NavigationTabFragmentPresenter navigationTabFragmentPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onAttach()
     */
    @Override
    public void init(){
        setPresenter();
        navigationTabFragmentPresenter.onViewReady(this);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_tab, container, false);
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
        navigationTabFragmentPresenter = new NavigationTabFragmentPresenterImpl();
    }


    public interface OnFragmentInteractionListener {

    }
}
