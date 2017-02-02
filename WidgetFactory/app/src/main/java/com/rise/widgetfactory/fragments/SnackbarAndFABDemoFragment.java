package com.rise.widgetfactory.fragments;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rise.widgetfactory.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SnackbarAndFABDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SnackbarAndFABDemoFragment extends Fragment {

    private View rootView = null;
    private CoordinatorLayout coordinatorLayout = null;
    private Button btnShowSnackBar = null;

    public SnackbarAndFABDemoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SnackbarAndFABDemoFragment.
     */
    public static SnackbarAndFABDemoFragment newInstance() {
        SnackbarAndFABDemoFragment fragment = new SnackbarAndFABDemoFragment();
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
        rootView = inflater.inflate(R.layout.fragment_snackbar_and_fab_demo, container, false);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
        btnShowSnackBar = (Button) rootView.findViewById(R.id.btn_show_snack_bar);
        btnShowSnackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "Hey, SnackBar here!!", Snackbar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });

        return rootView;
    }

}
