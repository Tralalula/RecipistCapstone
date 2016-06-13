package com.example.tobias.recipist.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.widget.ImageView;

import com.example.tobias.recipist.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tobias on 08-06-2016.
 */
public class ViewRecipeActivity extends AppCompatActivity {
//    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
//    @BindView(R.id.photo) ImageView mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_recipe);
//        ButterKnife.bind(this);

        ImageView imageView = (ImageView) findViewById(R.id.photo);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
//        collapsingToolbarLayout.setTitle("Apple Pie");

        Picasso.with(this)
                .load(R.drawable.a)
                .fit()
                .into(imageView);
    }

}
