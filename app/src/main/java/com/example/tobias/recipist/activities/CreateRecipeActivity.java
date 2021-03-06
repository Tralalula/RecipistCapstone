package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

import camera.AddPhoto;
import data.Ingredients;
import data.Recipe;
import data.Steps;
import util.Utilities;

// For handling camera images I used the following guides:
// http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
// http://blog-emildesign.rhcloud.com/?p=590
// https://developer.android.com/training/camera/photobasics.html
public class CreateRecipeActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";

    private EditText mRecipeTitle;
    private Switch mRecipeProgress;
    private EditText mRecipeTime;
    private EditText mRecipeServings;

    private ImageView mRecipeImage;
    private Bitmap mRecipeBitmapImage;

    private Toolbar mToolbar;

    private Button mRecipeEditIngredients;
    private Button mRecipeEditSteps;
    private FloatingActionButton mSaveRecipe;

    private ArrayList<Ingredients.Ingredient> ingredients;
    private ArrayList<Steps.Step> steps;

    private LinearLayout mRecipeIngredientsList;

    private AddPhoto mCamera;

    private LinearLayout mRecipeStepsList;

    private boolean editMode;
    private String mRecipeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mRecipeTitle = (EditText) findViewById(R.id.recipe_title);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);
        mRecipeProgress = (Switch) findViewById(R.id.recipe_progress);
        mRecipeTime = (EditText) findViewById(R.id.recipe_time);
        mRecipeServings = (EditText) findViewById(R.id.recipe_servings);
        mRecipeEditIngredients = (Button) findViewById(R.id.recipe_edit_ingredients);
        mRecipeEditSteps = (Button) findViewById(R.id.recipe_edit_steps);
        mRecipeIngredientsList = (LinearLayout) findViewById(R.id.recipe_ingredients_list);
        mSaveRecipe = (FloatingActionButton) findViewById(R.id.save_recipe);

        mRecipeStepsList = (LinearLayout) findViewById(R.id.recipe_steps_list);

        Recipe r = getIntent().getParcelableExtra("EDIT RECIPE");
        mRecipeKey = getIntent().getStringExtra("RECIPE KEY");
        if (r != null && mRecipeKey != null) {
            editMode = true;
            System.out.println("EDIT RECIPE " + r.title);
            System.out.println("RECIPE KEY " + mRecipeKey);

            handleEditRecipeMode(r);
        }

//        Ingredients.Ingredient ingredient = new Ingredients.Ingredient("100", "grams", "sugar");
//        ingredients.add(ingredient);

        if (mRecipeImage != null) mRecipeImage.setOnClickListener(this);
        if (mRecipeEditIngredients != null) mRecipeEditIngredients.setOnClickListener(this);
        if (mSaveRecipe != null) mSaveRecipe.setOnClickListener(this);
        if (mRecipeEditSteps != null) mRecipeEditSteps.setOnClickListener(this);

//        for (Ingredients.Ingredient d : ingredients) {
//            System.out.println("CREME BRULEE " + d.getIngredient());
//        }

        if (ingredients == null) {
            ingredients = new ArrayList<>();
            updateIngredients();
        }

        if (steps == null) {
            steps = new ArrayList<>();
            updateSteps();
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

    private void handleEditRecipeMode(Recipe recipe) {
        String image = recipe.image;
        String title = recipe.title;
        String time = recipe.time;
        String servings = recipe.servings;

        ingredients = recipe.ingredients;
        steps = recipe.steps;

        if (!Utilities.isNullOrEmpty(image)) {
            Bitmap bitmap = Utilities.convertBase64ToBitmap(image);
            mRecipeImage.setImageBitmap(bitmap);
            mRecipeBitmapImage = bitmap;
        }

        if (!Utilities.isNullOrEmpty(title)) mRecipeTitle.setText(title);
        if (!Utilities.isNullOrEmpty(time)) mRecipeTime.setText(time);
        if (!Utilities.isNullOrEmpty(servings)) mRecipeServings.setText(servings);
        mRecipeProgress.setChecked(recipe.progress);

        updateIngredients();
        updateSteps();
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
            case R.id.recipe_edit_steps:
                Intent stepIntent = new Intent(this, CreateStepActivity.class);
                stepIntent.putParcelableArrayListExtra("STEPS", steps);
                startActivityForResult(stepIntent, 667);
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
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == AddPhoto.SELECT_FILE_CODE) {
                Bitmap bitmap = mCamera.onSelectFromGalleryResult(data);
                mRecipeImage.setImageBitmap(bitmap);
                mRecipeBitmapImage = bitmap;
            } else if (requestCode == AddPhoto.REQUEST_CAMERA_CODE) {
                Bitmap bitmap = mCamera.onCaptureImageResult();
                mRecipeImage.setImageBitmap(bitmap);
                mRecipeBitmapImage = bitmap;
            } else if (requestCode == 666) {
                ingredients = data.getParcelableArrayListExtra("INGREDIENTS");
                updateIngredients();
            } else if (requestCode == 667) {
                steps = data.getParcelableArrayListExtra("STEPS");
                updateSteps();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Utilities.TOOLBAR_NAVIGATION_ICON_CLICK_ID:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                        ingredient.getIngredient(),
                        Typeface.NORMAL
                );
            }
        }
    }

    private void updateSteps() {
        mRecipeStepsList.removeAllViews();

        if (steps == null || steps.isEmpty()) {
            addToLinearLayout(this, mRecipeStepsList, "NOT AVAILABLE...", Typeface.NORMAL);
        } else {
            for (final Steps.Step step : steps) {
                addToLinearLayout(this, mRecipeStepsList, step.getMethod(), Typeface.NORMAL);
            }
        }
    }

    private void addToLinearLayout(Context context, LinearLayout linearLayout, String text, int typeface) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTypeface(null, typeface);
        linearLayout.addView(textView);
    }

    //    private String mImage = null;
    private void submitRecipe() {
        final String title = mRecipeTitle.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mRecipeTitle.setError("Required");
            return;
        }

        String image = null;
        if (mRecipeBitmapImage != null && !mRecipeBitmapImage.isRecycled()) {
            image = Utilities.decodeBitmapToBase64String(mRecipeBitmapImage);
        }
//        try {
//            decodeImage();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        final boolean progress = mRecipeProgress.isChecked();
        final String time = mRecipeTime.getText().toString();
        final String servings = mRecipeServings.getText().toString();

        writeNewRecipe(title, image, progress, time, servings, ingredients, steps);

        finish();
    }

    private void writeNewRecipe(String title, String image, boolean progress, String time,
                                String servings, ArrayList<Ingredients.Ingredient> ingredients, ArrayList<Steps.Step> steps) {
        String key;
        if (!editMode) key = mDatabase.child("recipes").push().getKey();
        else key = mRecipeKey;

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

    // http://stackoverflow.com/questions/12897205/android-progress-dialog-not-showing
//    public void decodeImage() throws IOException {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        Thread thread = new Thread() {
//            public void Run() {
//                if (mRecipeBitmapImage != null && !mRecipeBitmapImage.isRecycled()) {
//                    mImage = mCamera.decodeBitmapToBase64String(mRecipeBitmapImage);
//                }
//                progressDialog.dismiss();
//            }
//        };
//        thread.start();
//    }
}