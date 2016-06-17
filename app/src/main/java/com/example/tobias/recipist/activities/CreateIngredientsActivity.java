package com.example.tobias.recipist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.tobias.recipist.R;

import java.util.ArrayList;

import data.Ingredients;

/**
 * Created by Tobias on 17-06-2016.
 */
public class CreateIngredientsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Ingredients.Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredients);

        TextView textView = (TextView) findViewById(R.id.lol);
        if (getIntent() != null)
            ingredients = getIntent().getParcelableArrayListExtra("INGREDIENTS");
        if (textView != null) textView.setText(ingredients.get(0).getIngredient());

        ingredients.add(new Ingredients.Ingredient("150", "kilos", "cake"));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("INGREDIENTS", ingredients);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }
}
