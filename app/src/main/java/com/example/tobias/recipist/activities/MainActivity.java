package com.example.tobias.recipist.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tobias.recipist.R;

import adapters.MainPageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        if (viewPager != null) {
            viewPager.setAdapter(mainPageAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        // http://androidforums.com/threads/how-to-change-color-of-menu-item-icon-in-android.916282/
//        Drawable createRecipeIcon = menu.findItem(R.id.action_create_recipe).getIcon();
//        if (createRecipeIcon != null) {
////            createRecipeIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
//        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_recipe:
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}