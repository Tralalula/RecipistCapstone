package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tobias.recipist.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import camera.AddPhoto;
import data.Ingredients;
import data.Recipe;
import data.Step;
import util.Utilities;

// For handling camera images I used the following guides:
// http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
// http://blog-emildesign.rhcloud.com/?p=590
// https://developer.android.com/training/camera/photobasics.html
public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";

    private EditText mRecipeTitle;
    private Switch mRecipeProgress;
    private EditText mRecipeTime;
    private EditText mRecipeServings;

    private ImageView mRecipeImage;
    private Bitmap mRecipeBitmapImage;

    private Button mRecipeEditIngredients;
    private Button mRecipeAddStep;
    private FloatingActionButton mSaveRecipe;

    private ArrayList<Ingredients.Ingredient> ingredients;

    private LinearLayout mRecipeIngredientsList;

    private AddPhoto mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecipeTitle = (EditText) findViewById(R.id.recipe_title);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mRecipeProgress = (Switch) findViewById(R.id.recipe_progress);
        mRecipeTime = (EditText) findViewById(R.id.recipe_time);
        mRecipeServings = (EditText) findViewById(R.id.recipe_servings);
        mRecipeEditIngredients = (Button) findViewById(R.id.recipe_edit_ingredients);
        mRecipeAddStep = (Button) findViewById(R.id.add_step);
        mRecipeIngredientsList = (LinearLayout) findViewById(R.id.recipe_ingredients_list);
        mSaveRecipe = (FloatingActionButton) findViewById(R.id.save_recipe);

//        Ingredients.Ingredient ingredient = new Ingredients.Ingredient("100", "grams", "sugar");
//        ingredients.add(ingredient);

        if (mRecipeImage != null) mRecipeImage.setOnClickListener(this);
        if (mRecipeEditIngredients != null) mRecipeEditIngredients.setOnClickListener(this);
        if (mSaveRecipe != null) mSaveRecipe.setOnClickListener(this);
        if (mRecipeAddStep != null) mRecipeAddStep.setOnClickListener(this);

//        for (Ingredients.Ingredient d : ingredients) {
//            System.out.println("CREME BRULEE " + d.getIngredient());
//        }

        if (ingredients == null) {
            ingredients = new ArrayList<>();
            updateIngredients();
        }

        mCamera = new AddPhoto(this);

//        ArrayList<Ingredients.Ingredient> i = new ArrayList<>();
//        i.add(new Ingredients.Ingredient("", "", "150 kilo pancakes"));
//
//        List<Step> s = new ArrayList<>();
//        s.add(new Step("1", "whisk whisk whisk", "n/a", 10));
//        s.add(new Step("2", "whisk whisk whisk", "n/a", 10));
//        s.add(new Step("3", "whisk whisk whisk", "n/a", 10));
//
//
//        writeNewRecipe("Test", mCamera.decodeDrawableToBase64String(R.drawable.a), true, 60, "8-12", i, s);
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
                Intent ingredientIntent = new Intent(this, CreateIngredientsActivity.class);
                ingredientIntent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
                startActivityForResult(ingredientIntent, 666);
                break;
            case R.id.save_recipe:
                submitRecipe();
                break;
            case R.id.add_step:
                Intent stepIntent = new Intent(this, CreateStepActivity.class);
                startActivity(stepIntent);
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

    private void submitRecipe() {
        final String title = mRecipeTitle.getText().toString();
        String image = null;
        if (mRecipeBitmapImage != null && !mRecipeBitmapImage.isRecycled()) {
            image = mCamera.decodeBitmapToBase64String(mRecipeBitmapImage);
        }
        final boolean progress = mRecipeProgress.isChecked();
        final String time = mRecipeTime.getText().toString();
        final String servings = mRecipeServings.getText().toString();

        List<Step> testSteps = new ArrayList<>();
        testSteps.add(new Step("1", "whisk whisk whisk", "n/a", 10));

        writeNewRecipe(title, image, progress, time, servings, ingredients, testSteps);
    }

    private void writeNewRecipe(String title, String image, boolean progress, String time,
                                String servings, ArrayList<Ingredients.Ingredient> ingredients, List<Step> steps) {
        String key = mDatabase.child("recipes").push().getKey();
        Recipe recipe = new Recipe(
                title,
                image,
                progress,
                time,
                servings,
                ingredients,
                steps
        );
        mDatabase.child("recipes").child(key).setValue(recipe);
    }
}