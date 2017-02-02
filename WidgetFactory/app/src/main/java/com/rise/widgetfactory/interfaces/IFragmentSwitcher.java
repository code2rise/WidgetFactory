package com.rise.widgetfactory.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by RupeshCHAVAN on 01-02-2017.
 */

public interface IFragmentSwitcher {
    void switchFragment(Fragment mFragment, String TAG, boolean addToBackstack);
}
