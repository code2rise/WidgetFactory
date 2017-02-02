package com.rise.widgetfactory.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.interfaces.IFragmentSwitcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoordinatorLayoutDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoordinatorLayoutDemoFragment extends Fragment implements View.OnClickListener {

    private View rootView = null;
    private Button btnSnackbarFabDemo = null;
    private Button btnFabFollowsWidget = null;
    private Button btnCollapsingtoolbarAndAppbarDemo = null;
    private Button btnCustomAnimation = null;

    public CoordinatorLayoutDemoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CoordinatorLayoutDemoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoordinatorLayoutDemoFragment newInstance(String param1, String param2) {
        CoordinatorLayoutDemoFragment fragment = new CoordinatorLayoutDemoFragment();
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
        rootView = inflater.inflate(R.layout.fragment_coordinator_layout_demo, container, false);
        btnSnackbarFabDemo = (Button) rootView.findViewById(R.id.btn_snackbar_fab_demo);
        btnFabFollowsWidget = (Button) rootView.findViewById(R.id.btn_fab_follows_widget);
        btnCollapsingtoolbarAndAppbarDemo = (Button) rootView.findViewById(R.id.btn_collapsingtoolbar_and_appbar_demo);
        btnCustomAnimation = (Button) rootView.findViewById(R.id.btn_custom_animation);

        btnSnackbarFabDemo.setOnClickListener(this);
        btnFabFollowsWidget.setOnClickListener(this);
        btnCollapsingtoolbarAndAppbarDemo.setOnClickListener(this);
        btnCustomAnimation.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_snackbar_fab_demo: {
                if(getActivity() instanceof IFragmentSwitcher) {
                    ((IFragmentSwitcher) getActivity()).switchFragment(SnackbarAndFABDemoFragment.newInstance(),
                            "SnackBarAndFABDemo", true);
                }
                break;
            }
            case R.id.btn_fab_follows_widget: {
                if(getActivity() instanceof IFragmentSwitcher) {
                    ((IFragmentSwitcher) getActivity()).switchFragment(FABFollowsWidgetFragment.newInstance(),
                            "FABFollowsWidgetDemo", true);
                }
                break;
            }
            case R.id.btn_collapsingtoolbar_and_appbar_demo: {
                if(getActivity() instanceof IFragmentSwitcher) {
                    ((IFragmentSwitcher) getActivity()).switchFragment(CollapsibleToolbarAndAppbarDemoFragment.newInstance(),
                            "CollapsibleToolbarAndAppbarDemo", true);
                }
                break;
            }
            case R.id.btn_custom_animation: {
                break;
            }
        }
    }
}
