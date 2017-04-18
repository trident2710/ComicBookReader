package com.dev.trident.comicbookreader.fragments.multiplepageview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.trident.comicbookreader.R;

/**

 */
public class MultipageFragment extends Fragment{

    private OnFragmentInteractionListener mListener;


    /**
     * Initialisation of key components of this activity
     * Should be called in onAttach()
     */
    public void init(){
    }

    public MultipageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultipageFragment.
     */
    public static MultipageFragment newInstance(String param1, String param2) {
        MultipageFragment fragment = new MultipageFragment();
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

        return inflater.inflate(R.layout.fragment_multipage, container, false);
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
        void onFragmentInteraction(Uri uri);
    }


}
