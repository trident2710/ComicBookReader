package com.dev.trident.comicbookreader.fragments.navigationtab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dev.trident.comicbookreader.R;

/**

 */
public class NavigationTabFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


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



    public interface OnFragmentInteractionListener {

    }

}
