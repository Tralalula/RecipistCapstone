package com.example.tobias.recipist.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.tobias.recipist.R;

import adapters.MainPageAdapter;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            mViewPager = (ViewPager) findViewById(R.id.pager);
            if (mViewPager != null) {
                mViewPager.setAdapter(mainPageAdapter);
                mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
            }

            for (int i = 0; i < mainPageAdapter.getCount(); i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mainPageAdapter.getPageTitle(i))
                                .setTabListener(this));
            }
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}