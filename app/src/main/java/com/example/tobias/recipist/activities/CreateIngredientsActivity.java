package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tobias.recipist.R;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import data.Ingredients;
import util.Utilities;

/**
 * Created by Tobias on 17-06-2016.
 */
public class CreateIngredientsActivity extends AppCompatActivity implements View.OnClickListener {
//    private String[] measurements = {
//            "g", "gr", "gram", "grams",
//            "cups", "cup",
//            "tsp", "tsps", "teaspoon", "teaspoons",
//            "tbsp", "tbsps", "tablespoon", "tablespoons",
//            "kg", "kgs", "kilo", "kilos", "kilogram", "kilograms",
//            "oz", "ozs", "ounce", "ounces",
//            "pound", "pounds"
//    };

    private ArrayList<Ingredients.Ingredient> oldIngredients;
    private ArrayList<Ingredients.Ingredient> newIngredients;

    private Menu mMenu;

    ImageView sortIngredients;
    Button addIngredient;
    DragLinearLayout dragLinearLayout;
    FloatingActionButton floatingActionButton;

    private boolean mSortIngredientsMode;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredients);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mSortIngredientsMode = true;

        addIngredient = (Button) findViewById(R.id.add_ingredient);
        if (addIngredient != null) addIngredient.setOnClickListener(this);

        dragLinearLayout = (DragLinearLayout) findViewById(R.id.lin);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.save_ingredients);
        if (floatingActionButton != null) floatingActionButton.setOnClickListener(this);

        if (getIntent() != null) {
            oldIngredients = getIntent().getParcelableArrayListExtra("INGREDIENTS");
            newIngredients = oldIngredients;
            if (!oldIngredients.isEmpty()) {
                for (Ingredients.Ingredient ingredient : newIngredients) {
//                System.out.println("MEOW CAKE IS GOOD " + ingredient.getIngredient());
                    addEditTextViewToDragLinearLayout(ingredient.getQuantity() + ingredient.getMeasure() + ingredient.getIngredient());
                }
            } else {
                addEmptyEditTextView();
            }
        }

        handleIngredientsMode();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<EditText> arraylist = (ArrayList<EditText>) savedInstanceState.getSerializable("ARRAYLIST");
        dragLinearLayout.removeAllViews();
        if (arraylist != null) {
            for (EditText editText : arraylist) {
                ((ViewGroup) editText.getParent()).removeView(editText);
                dragLinearLayout.addView(editText);
            }
        }

        mSortIngredientsMode = savedInstanceState.getBoolean("SORTMODE");
        handleIngredientsMode();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<EditText> editTexts = new ArrayList<>();
        int count = dragLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = dragLinearLayout.getChildAt(i);
            EditText editTextChild = (EditText) child;
            editTexts.add(editTextChild);
        }
        outState.putSerializable("ARRAYLIST", editTexts);
        outState.putBoolean("SORTMODE", !mSortIngredientsMode);

        super.onSaveInstanceState(outState);
    }

    private void addEditTextViewToDragLinearLayout(String text) {
        EditText editText = new EditText(this);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        editText.setText(text);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        if (dragLinearLayout != null) {
            dragLinearLayout.addView(editText);
        }

        updateEditTextsToCurrentMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_ingredients_toolbar, menu);
        mMenu = menu;
        swapMode();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_ingredients:
                handleIngredientsMode();
                swapMode();
                return true;
            case Utilities.TOOLBAR_NAVIGATION_ICON_CLICK_ID:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void swapMode() {
        if (!mSortIngredientsMode) {
            handleSortMode(getDrawable(R.drawable.done_black), true);
        } else {
            handleSortMode(getDrawable(R.drawable.sort), false);
        }
    }

    private void handleIngredientsMode() {
        for (int i = 0; i < (dragLinearLayout != null ? dragLinearLayout.getChildCount() : 0); i++) {
            View child = dragLinearLayout.getChildAt(i);
            EditText editTextChild = (EditText) child;

            if (!mSortIngredientsMode) {
                addImageToTheRightOfEditText(editTextChild, getDrawable(R.drawable.ic_swap_vert_black_24dp));
                dragLinearLayout.setViewDraggable(child, child);
            } else {
                addImageToTheRightOfEditText(editTextChild, getDrawable(R.drawable.ic_delete_black_24dp));
                makeEditTextDeletableFromDragLinearLayout(editTextChild, dragLinearLayout, 2);
            }
        }
    }

    private void updateEditTextsToCurrentMode() {
        for (int i = 0; i < (dragLinearLayout != null ? dragLinearLayout.getChildCount() : 0); i++) {
            View child = dragLinearLayout.getChildAt(i);
            EditText editTextChild = (EditText) child;

            if (mSortIngredientsMode) {
                addImageToTheRightOfEditText(editTextChild, getDrawable(R.drawable.ic_swap_vert_black_24dp));
                dragLinearLayout.setViewDraggable(child, child);
            } else {
                addImageToTheRightOfEditText(editTextChild, getDrawable(R.drawable.ic_delete_black_24dp));
                makeEditTextDeletableFromDragLinearLayout(editTextChild, dragLinearLayout, 2);
            }
        }
    }

    private void handleSortMode(Drawable drawable, boolean sortMode) {
        mMenu.getItem(0).setIcon(drawable);
        mSortIngredientsMode = sortMode;
    }

    private void addImageToTheRightOfEditText(EditText editText, Drawable image) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, image, null);
    }

    // http://stackoverflow.com/questions/13135447/setting-onclicklistner-for-the-drawable-right-of-an-edittext/26269435#26269435
    private void makeEditTextDeletableFromDragLinearLayout(final EditText editText, final DragLinearLayout dragLinearLayout, final int IMAGE_POSITION) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[IMAGE_POSITION].getBounds().width())) {
                        dragLinearLayout.removeView(editText);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
//        System.out.println("v.getId() = " + v.getId());
        switch (v.getId()) {
            case R.id.add_ingredient:
                addEmptyEditTextView();
                break;
            case R.id.save_ingredients:
                Intent intent = new Intent();

                newIngredients = new ArrayList<>();

                for (int i = 0; i < (dragLinearLayout != null ? dragLinearLayout.getChildCount() : 0); i++) {
                    View child = dragLinearLayout.getChildAt(i);
                    EditText editTextChild = (EditText) child;

                    if (!isEditTextEmpty(editTextChild)) {
//                        newIngredients.add(decodeIngredientString(editTextChild.getText().toString()));
//                        decodeString(editTextChild.getText().toString());
                        newIngredients.add(new Ingredients.Ingredient("", "", editTextChild.getText().toString()));
                    }
                }

                intent.putParcelableArrayListExtra("INGREDIENTS", newIngredients);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void addEmptyEditTextView() {
        EditText editText = new EditText(this);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        editText.setHint("150 grams of sugar");
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        if (dragLinearLayout != null) dragLinearLayout.addView(editText);
        updateEditTextsToCurrentMode();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("INGREDIENTS", oldIngredients);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

//    private Ingredients.Ingredient decodeIngredientString(String ingredientString) {
//        String quantity = "";
//        String measurement = "";
//        String ingredient = "";
//
//        boolean foundQuantity = false;
//        for (int i = 0; i < ingredientString.length(); i++) {
//            System.out.println("FOR " + ingredientString.charAt(i));
//            char currentChar = ingredientString.charAt(i);
//            if (Character.isDigit(currentChar) && !foundQuantity) quantity += currentChar;
//            else foundQuantity = true;
//
//            if (foundQuantity) {
//                if (ingredientString.contains(" ")) {
//                    String checkForMeasurement = ingredientString.substring(
//                            i,
//                            ingredientString.indexOf(" ")
//                    );
//                    System.out.println("LOL " + checkForMeasurement);
//                    System.out.println("MEOW " + ingredientString.substring(i));
//
//                    if (Arrays.asList(measurements).contains(checkForMeasurement)) {
//                        measurement = checkForMeasurement;
//                        ingredient = ingredientString.substring(ingredientString.indexOf(" ") + 1);
//                    } else {
//                        ingredient = ingredientString.substring(i);
//                    }
//                } else {
//                    ingredient = ingredientString.substring(i);
//                }
//                break;
//            }
//        }
//
//        System.out.println(quantity.contains(" "));
//        System.out.println(measurement.contains(" "));
//        System.out.println(ingredient.contains(" "));
//        System.out.println("PEW PEW :" + quantity + ": :" + measurement + ": :" + ingredient);
//        return new Ingredients.Ingredient(quantity, measurement, ingredient);
//    }

//    private void decodeString(String ingredientString) {
//        String quantity = "";
//        String measurement = "";
//        String ingredient = "";
//
//        boolean foundQuantity = false;
//        for (int i = 0; i < ingredientString.length(); i++) {
//            char currentChar = ingredientString.charAt(i);
//            if (Character.isDigit(currentChar) && !foundQuantity) quantity += currentChar;
//            else foundQuantity = true;
//
//            if (foundQuantity) {
//                String tempMeasurement = ingredientString.substring(i);
//                boolean forLoopComplete = false;
//                for (int j = 0; j < tempMeasurement.length(); j++) {
//                    char tempMeasurementChar = tempMeasurement.charAt(j);
//                    if (tempMeasurementChar != ' ') {
//                        String tempMeasurementWithoutSpacesAtStart = tempMeasurement.substring(j);
//                        if (tempMeasurementWithoutSpacesAtStart.contains(" ")) {
//                            String checkForMeasurement = tempMeasurementWithoutSpacesAtStart.substring(j, tempMeasurementWithoutSpacesAtStart.indexOf(' '));
//                            if (Arrays.asList(measurements).contains(checkForMeasurement)) {
//                                measurement = checkForMeasurement;
//                                ingredient = tempMeasurementWithoutSpacesAtStart.substring(tempMeasurementWithoutSpacesAtStart.indexOf(' ') + 1);
//                            } else {
//                                ingredient = tempMeasurementWithoutSpacesAtStart;
//                            }
//                        } else {
//                            ingredient = tempMeasurementWithoutSpacesAtStart;
//                        }
//                    }
//
//                    if (j == tempMeasurement.length() - 1) forLoopComplete = true;
//                }
//
//                if (forLoopComplete) ingredient = tempMeasurement;
//
//                break;
//            }
//        }
//
//        System.out.println("TEST TEST |" + quantity + "| |" + measurement + "| |" + ingredient);
//    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
