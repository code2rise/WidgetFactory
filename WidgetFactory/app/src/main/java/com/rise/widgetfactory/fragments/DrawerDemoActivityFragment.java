package com.rise.widgetfactory.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rise.widgetfactory.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DrawerDemoActivityFragment extends Fragment {

    public DrawerDemoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer_demo, container, false);
    }
}
