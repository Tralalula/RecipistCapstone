package com.example.tobias.recipist.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.Arrays;

import data.Ingredients;

/**
 * Created by Tobias on 17-06-2016.
 */
public class CreateIngredientsActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] measurements = {"g", "gr", "gram", "grams"};

    private ArrayList<Ingredients.Ingredient> ingredients;

    EditText ingredient;
    Button addIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredients);

        addIngredient = (Button) findViewById(R.id.add_ingredient);
        if (addIngredient != null) addIngredient.setOnClickListener(this);

//        TextView textView = (TextView) findViewById(R.id.lol);
        ingredient = (EditText) findViewById(R.id.ingredient);
        if (getIntent() != null)
            ingredients = getIntent().getParcelableArrayListExtra("INGREDIENTS");
//        if (textView != null) textView.setText(ingredients.get(0).getIngredient());

//        ingredients.add(new Ingredients.Ingredient("150", "kilos", "cake"));
        decodeIngredientString("150g sugar");

//        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coord_layout);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin);

        final EditText editText = new EditText(this);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        editText.setHint("150 kilos of pancakes");
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable draw = getDrawable(R.drawable.ic_delete_black_24dp);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
//        editText.setCompoundDrawables(null, null, drawable, null);

        if (linearLayout != null) {
            linearLayout.addView(editText);
        }

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (linearLayout != null) {
                            linearLayout.removeView(editText);
                        }
                        System.out.println("YOU CLICKED IT, MUAHAHAH");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_ingredient:
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin);

                EditText editText = new EditText(this);
                editText.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                );

                editText.setHint("150 kilos of pancakes");
                editText.setMaxLines(1);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);

                if (linearLayout != null) {
                    linearLayout.addView(editText);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        if (!isEditTextEmpty(ingredient)) {
            ingredients.add(new Ingredients.Ingredient(
                    "150",
                    "kilos",
                    ingredient.getText().toString())
            );
        }

        intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    private void decodeIngredientString(String ingredientString) {
        String quantity = "";
        String measurement = "";
        String ingredient = "";

        boolean foundQuantity = false;
        for (int i = 0; i < ingredientString.length(); i++) {
            System.out.println("FOR " + ingredientString.charAt(i));
            char currentChar = ingredientString.charAt(i);
            if (Character.isDigit(currentChar) && !foundQuantity) quantity += currentChar;
            else foundQuantity = true;

            if (foundQuantity) {
                if (ingredientString.contains(" ")) {
                    String checkForMeasurement = ingredientString.substring(
                            i,
                            ingredientString.indexOf(" ")
                    );
                    System.out.println("LOL " + checkForMeasurement);
                    System.out.println("MEOW " + ingredientString.substring(i));

                    if (Arrays.asList(measurements).contains(checkForMeasurement)) {
                        measurement = checkForMeasurement;
                        ingredient = ingredientString.substring(ingredientString.indexOf(" ") + 1);
                    } else {
                        ingredient = ingredientString.substring(i);
                    }
                } else {
                    ingredient = ingredientString.substring(i);
                }
                break;
            }
        }

        System.out.println(quantity.contains(" "));
        System.out.println(measurement.contains(" "));
        System.out.println(ingredient.contains(" "));
        System.out.println("PEW PEW " + quantity + " " + measurement + " " + ingredient);
    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
