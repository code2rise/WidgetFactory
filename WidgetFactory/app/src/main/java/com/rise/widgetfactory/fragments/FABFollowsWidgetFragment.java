package com.rise.widgetfactory.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rise.widgetfactory.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FABFollowsWidgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FABFollowsWidgetFragment extends Fragment {

    private View rootView = null;

    public FABFollowsWidgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FABFollowsWidgetFragment.
     */
    public static FABFollowsWidgetFragment newInstance() {
        FABFollowsWidgetFragment fragment = new FABFollowsWidgetFragment();

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
        rootView = inflater.inflate(R.layout.fragment_fabfollows_widget, container, false);
        return rootView;
    }

}
