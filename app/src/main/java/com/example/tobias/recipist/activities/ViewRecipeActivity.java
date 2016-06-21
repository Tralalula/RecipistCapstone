package com.example.tobias.recipist.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobias.recipist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import data.Ingredients;
import data.Recipe;
import data.Steps;
import util.Utilities;

/**
 * Created by Tobias on 08-06-2016.
 */
public class ViewRecipeActivity extends AppCompatActivity {
    private static final String TAG = ViewRecipeActivity.class.toString();

    private DatabaseReference mRecipeReference;
    private ValueEventListener mRecipeListener;

    private String mRecipeKey;

    private ImageView mRecipeImage;
    private TextView mRecipeImageNotAvailable;

    private TextView mRecipeTitle;
    private TextView mRecipeProgress;
    private TextView mRecipeTime;
    private TextView mRecipeServings;
    private LinearLayout mRecipeIngredientsList;
    private LinearLayout mRecipeStepsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_recipe);

        // Get Recipe key from intent
        mRecipeKey = getIntent().getStringExtra(Utilities.VIEW_RECIPE_ACTIVITY_RECIPE_KEY);
        if (mRecipeKey == null) {
            throw new IllegalArgumentException(getString(R.string.error_view_recipe_activity_intent_is_null));
        }
//        System.out.println("mRecipeKey = " + mRecipeKey);

        // Initialize Database
        mRecipeReference = FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.firebase_database_recipes))
                .child(mRecipeKey);

        // Initialize Views
        mRecipeImage = (ImageView) findViewById(R.id.photo);
        mRecipeImageNotAvailable = (TextView) findViewById(R.id.image_not_available);

        mRecipeTitle = (TextView) findViewById(R.id.recipe_title);
        mRecipeProgress = (TextView) findViewById(R.id.recipe_progress);
        mRecipeTime = (TextView) findViewById(R.id.recipe_time);
        mRecipeServings = (TextView) findViewById(R.id.recipe_servings);

        mRecipeIngredientsList = (LinearLayout) findViewById(R.id.recipe_ingredients_list);
        mRecipeStepsList = (LinearLayout) findViewById(R.id.recipe_steps_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener recipeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String notSpecified = getString(R.string.not_specified);

                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                String image = recipe.image;
                String title = recipe.title;
                String time = recipe.time;
                String servings = recipe.servings;

                boolean progress = recipe.progress;
                String progressText;

                ArrayList<Ingredients.Ingredient> ingredients = recipe.ingredients;
                ArrayList<Steps.Step> steps = recipe.steps;

                if (Utilities.isNullOrEmpty(image)) {
                    mRecipeImage.setVisibility(View.GONE);
                    mRecipeImageNotAvailable.setVisibility(View.VISIBLE);
                } else {
                    mRecipeImage.setVisibility(View.VISIBLE);
                    mRecipeImageNotAvailable.setVisibility(View.GONE);
                    mRecipeImage.setImageBitmap(Utilities.convertBase64ToBitmap(image));
                }

                if (Utilities.isNullOrEmpty(title)) title = notSpecified;
                if (Utilities.isNullOrEmpty(time)) time = notSpecified;
                if (Utilities.isNullOrEmpty(servings)) servings = notSpecified;

                if (progress) progressText = getString(R.string.view_recipe_progress_true);
                else progressText = getString(R.string.view_recipe_progress_false);

                mRecipeTitle.setText(title);
                mRecipeProgress.setText(progressText);
                mRecipeTime.setText(time);
                mRecipeServings.setText(servings);

                handleIngredients(mRecipeIngredientsList, ingredients);
                handleSteps(mRecipeStepsList, steps);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, getString(R.string.view_recipe_load_recipe_on_cancelled_database_error),
                        databaseError.toException());

                Toast.makeText(ViewRecipeActivity.this,
                        getString(R.string.view_recipe_load_recipe_on_cancelled_toast_text),
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRecipeReference.addValueEventListener(recipeListener);

        // Keep copy of recipe listener so we can remove it when the app stops
        mRecipeListener = recipeListener;
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mRecipeListener != null) mRecipeReference.removeEventListener(mRecipeListener);
    }

    private void handleIngredients(LinearLayout linearLayout, ArrayList<Ingredients.Ingredient> ingredients) {
        linearLayout.removeAllViews();

        ViewGroup.LayoutParams textViewLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (ingredients == null || ingredients.isEmpty()) {
            TextView noIngredientsTextView = Utilities.newTextView(
                    ViewRecipeActivity.this,
                    textViewLayoutParams,
                    "No ingredients available.",
                    getResources().getColor(R.color.textColorSecondary));
            Utilities.addTextView(linearLayout, noIngredientsTextView);
        } else {
            for (Ingredients.Ingredient ingredient : ingredients) {
                TextView ingredientTextView = Utilities.newTextView(ViewRecipeActivity.this,
                        textViewLayoutParams,
                        ingredient.getIngredient(),
                        getResources().getColor(R.color.textColorPrimary));
                Utilities.addTextView(linearLayout, ingredientTextView);
            }
        }
    }

    private void handleSteps(LinearLayout linearLayout, ArrayList<Steps.Step> steps) {
        linearLayout.removeAllViews();

        ViewGroup.LayoutParams textViewLayoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (steps == null || steps.isEmpty()) {
            TextView noStepsTextView = Utilities.newTextView(
                    ViewRecipeActivity.this,
                    textViewLayoutParams,
                    "No steps available.",
                    getResources().getColor(R.color.textColorSecondary));
            Utilities.addTextView(linearLayout, noStepsTextView);
        } else {
            for (int i = 0; i < steps.size(); i++) {
                String textViewText = "<strong>" + (i + 1) + ".</strong> " + steps.get(i).getMethod();
                if (i != steps.size() - 1) {
                    textViewText += "<br>";
                }

                TextView stepTextView = Utilities.newTextView(ViewRecipeActivity.this,
                        textViewLayoutParams,
                        Html.fromHtml(textViewText),
                        getResources().getColor(R.color.textColorSecondary));
                Utilities.addTextView(linearLayout, stepTextView);
            }
        }
    }
}
