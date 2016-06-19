package com.example.tobias.recipist.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobias.recipist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import data.Recipe;

/**
 * Created by Tobias on 08-06-2016.
 */
public class ViewRecipeActivity extends AppCompatActivity {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = mRootRef.child("condition");

//    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
//    @BindView(R.id.photo) ImageView mPhoto;

    private TextView mRecipeTime;
    private ImageView mRecipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_recipe);
//        ButterKnife.bind(this);

        mRecipeImage = (ImageView) findViewById(R.id.photo);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
//        collapsingToolbarLayout.setTitle("Apple Pie");

        Picasso.with(this)
                .load(R.drawable.b)
                .fit()
                .into(mRecipeImage);


        mRecipeTime = (TextView) findViewById(R.id.recipe_time);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRootRef.child("recipes").child("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);

                System.out.println("pew pew boomerang " + recipe.time);
                mRecipeTime.setText(recipe.time + "");

                byte[] decodedString = Base64.decode(recipe.image, Base64.DEFAULT);
                Bitmap bitmapDecode = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                mRecipeImage.setImageBitmap(bitmapDecode);
//                Picasso.with(getApplicationContext())
//                        .load(bitmapDecode)
//                        .fit()
//                        .into(mRecipeImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
