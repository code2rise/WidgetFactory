package com.rise.widgetfactory.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.fragments.CoordinatorLayoutDemoFragment;
import com.rise.widgetfactory.fragments.SnackbarAndFABDemoFragment;
import com.rise.widgetfactory.interfaces.IFragmentSwitcher;

public class CoordinatorLayoutDemoActivity extends AppCompatActivity implements IFragmentSwitcher {

    private FrameLayout flFragmentContainer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_demo);

        flFragmentContainer = (FrameLayout) findViewById(R.id.fl_fragment_container);
        switchFragment(new CoordinatorLayoutDemoFragment(), "CoordinatorLayoutDemo", false);
    }

    @Override
    public void switchFragment(Fragment mFragment, String TAG, boolean addToBackstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(flFragmentContainer.getId(), mFragment, TAG);
        if(addToBackstack)
            fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }
}
