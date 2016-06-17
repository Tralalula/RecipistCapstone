package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredients);

//        TextView textView = (TextView) findViewById(R.id.lol);
        ingredient = (EditText) findViewById(R.id.ingredient);
        if (getIntent() != null)
            ingredients = getIntent().getParcelableArrayListExtra("INGREDIENTS");
//        if (textView != null) textView.setText(ingredients.get(0).getIngredient());

//        ingredients.add(new Ingredients.Ingredient("150", "kilos", "cake"));
        decodeIngredientString("150g sugar");
    }

    @Override
    public void onClick(View v) {

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
