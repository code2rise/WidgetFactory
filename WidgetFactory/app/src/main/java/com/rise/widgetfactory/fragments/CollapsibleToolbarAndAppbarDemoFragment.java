package com.rise.widgetfactory.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rise.widgetfactory.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollapsibleToolbarAndAppbarDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollapsibleToolbarAndAppbarDemoFragment extends Fragment {

    private View rootView = null;

    public CollapsibleToolbarAndAppbarDemoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CollapsibleToolbarAndAppbarDemoFragment.
     */
    public static CollapsibleToolbarAndAppbarDemoFragment newInstance() {
        CollapsibleToolbarAndAppbarDemoFragment fragment = new CollapsibleToolbarAndAppbarDemoFragment();
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
        rootView = inflater.inflate(R.layout.fragment_collapsible_toolbar_and_appbar_demo, container, false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsibleToolbar);
        collapsingToolbar.setTitle("CollapsibleToolbarDemo");
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#4545FF"));
        return rootView;
    }
}
