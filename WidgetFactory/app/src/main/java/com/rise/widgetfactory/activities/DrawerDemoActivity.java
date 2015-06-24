package com.rise.widgetfactory.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rise.widgetfactory.R;
import com.rise.widgetfactory.fragments.AlbumsFragment;
import com.rise.widgetfactory.fragments.ArtistsFragment;
import com.rise.widgetfactory.fragments.DrawerDemoActivityFragment;
import com.rise.widgetfactory.fragments.FoldersFragment;
import com.rise.widgetfactory.fragments.PlaylistFragment;

public class DrawerDemoActivity extends AppCompatActivity {

    private ActionBar actionBar = null;
    private DrawerLayout mDrawerLayout = null;
    private FrameLayout contentFrame = null;
    private ListView lvDrawerItems = null;
    private String[] drawerItems = null;
    private ActionBarDrawerToggle mDrawerToggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_demo);

        setupDrawer();
        showFragment(0);
    }

    private void setupDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        contentFrame = (FrameLayout) findViewById(R.id.content_frame);
        lvDrawerItems = (ListView) findViewById(R.id.left_drawer);

        drawerItems = getResources().getStringArray(R.array.drawer_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                drawerItems);
        lvDrawerItems.setAdapter(adapter);

        actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this,
                    mDrawerLayout,
                    R.string.drawer_open,
                    R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }

        lvDrawerItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Toast.makeText(DrawerDemoActivity.this, drawerItems[i], Toast.LENGTH_SHORT).show();
                showFragment(i);
                mDrawerLayout.closeDrawer(lvDrawerItems);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle != null) {
            mDrawerToggle.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0: {
                fragment = new DrawerDemoActivityFragment();
                break;
            }
            case 1: {
                fragment = new PlaylistFragment();
                break;
            }
            case 2: {
                fragment = new AlbumsFragment();
                break;
            }
            case 3: {
                fragment = new ArtistsFragment();
                break;
            }
            case 4: {
                fragment = new FoldersFragment();
                break;
            }
            default: {
                break;
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(contentFrame.getId(), fragment).commit();
    }
}
