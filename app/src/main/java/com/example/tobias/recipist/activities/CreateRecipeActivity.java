package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tobias.recipist.R;

import java.util.ArrayList;

import camera.AddPhoto;
import data.Ingredients;
import util.Utilities;

// For handling camera images I used the following guides:
// http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
// http://blog-emildesign.rhcloud.com/?p=590
// https://developer.android.com/training/camera/photobasics.html
public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private ImageView mRecipeImage;
    private Bitmap mRecipeBitmapImage;

    private Button mRecipeEditIngredients;


    private ArrayList<Ingredients.Ingredient> ingredients;

    private LinearLayout mRecipeIngredientsList;

    private AddPhoto mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);


        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mRecipeEditIngredients = (Button) findViewById(R.id.recipe_edit_ingredients);
        mRecipeIngredientsList = (LinearLayout) findViewById(R.id.recipe_ingredients_list);

//        Ingredients.Ingredient ingredient = new Ingredients.Ingredient("100", "grams", "sugar");
//        ingredients.add(ingredient);

        if (mRecipeImage != null) mRecipeImage.setOnClickListener(this);
        if (mRecipeEditIngredients != null) mRecipeEditIngredients.setOnClickListener(this);

//        for (Ingredients.Ingredient d : ingredients) {
//            System.out.println("CREME BRULEE " + d.getIngredient());
//        }

        if (ingredients == null) {
            ingredients = new ArrayList<>();
            updateIngredients();
        }

        mCamera = new AddPhoto(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mRecipeBitmapImage);
        outState.putParcelableArrayList("INGREDIENTS SAVE", ingredients);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipeBitmapImage = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
            if (mRecipeBitmapImage != null) mRecipeImage.setImageBitmap(mRecipeBitmapImage);
            ingredients = (ArrayList<Ingredients.Ingredient>) savedInstanceState.get("INGREDIENTS SAVE");

            for (Ingredients.Ingredient d : ingredients) {
                System.out.println("CREME BRULEE " + d.getIngredient());
            }
            updateIngredients();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recipe_image:
                mCamera.selectImage();
                break;
            case R.id.recipe_edit_ingredients:
                Intent intent = new Intent(this, CreateIngredientsActivity.class);
                intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                startActivityForResult(intent, 666);
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utilities.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                mCamera.onRequestPermissionsResult(grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddPhoto.SELECT_FILE_CODE) {
                Bitmap bitmap = mCamera.onSelectFromGalleryResult(data);
                mRecipeImage.setImageBitmap(bitmap);
                mRecipeBitmapImage = bitmap;
            } else if (requestCode == AddPhoto.REQUEST_CAMERA_CODE) {
                Bitmap bitmap = mCamera.onCaptureImageResult();
                mRecipeImage.setImageBitmap(bitmap);
                mRecipeBitmapImage = bitmap;
            } else if (requestCode == 666) {
                if (data != null) {
                    ingredients = data.getParcelableArrayListExtra("INGREDIENTS");
                    updateIngredients();
//                    for (Ingredients.Ingredient ingredient : ingredients) {
//                        System.out.println("PANCAKE " + ingredient.getIngredient());
//                    }
                }
            }
        }
    }

    private void updateIngredients() {
        mRecipeIngredientsList.removeAllViews();

        if (ingredients == null || ingredients.isEmpty()) {
            addToLinearLayout(this, mRecipeIngredientsList, "NOT AVAILABLE :'(", Typeface.NORMAL);
        } else {
            for (final Ingredients.Ingredient ingredient : ingredients) {
                addToLinearLayout(
                        this,
                        mRecipeIngredientsList,
                        ingredient.getMeasure() + ingredient.getQuantity() + ingredient.getIngredient(),
                        Typeface.NORMAL
                );
            }
        }
    }

    private void addToLinearLayout(Context context, LinearLayout linearLayout, String text, int typeface) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTypeface(null, typeface);
        linearLayout.addView(textView);
    }
}