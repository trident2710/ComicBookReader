package com.dev.trident.comicbookreader.fragments.multiplepageview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.trident.comicbookreader.MVPBasic.MessageType;
import com.dev.trident.comicbookreader.R;
import com.dev.trident.comicbookreader.fragments.multiplepageview.presenter.MultipageFragmentPresenter;
import com.dev.trident.comicbookreader.fragments.multiplepageview.presenter.MultipageFragmentPresenterImpl;
import com.dev.trident.comicbookreader.fragments.multiplepageview.view.MultipageFragmentView;

/**

 */
public class MultipageFragment extends Fragment implements MultipageFragmentView{

    private OnFragmentInteractionListener mListener;

    private MultipageFragmentPresenter multipageFragmentPresenter;
    /**
     * Initialisation of key components of this activity
     * Should be called in onAttach()
     */
    @Override
    public void init(){
        setPresenter();
        multipageFragmentPresenter.onViewReady(this);
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

    @Override
    public void setPresenter() {
        multipageFragmentPresenter = new MultipageFragmentPresenterImpl();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void showMessage(MessageType type, String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(MessageType type, int msgStringId) {
        Toast.makeText(getContext(),getContext().getString(msgStringId),Toast.LENGTH_SHORT).show();
    }
}
