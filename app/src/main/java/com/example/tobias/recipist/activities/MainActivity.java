package com.example.tobias.recipist.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tobias.recipist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.List;

import adapters.MainPageAdapter;
import data.Ingredient;
import data.Recipe;
import data.Step;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        if (viewPager != null) {
            viewPager.setAdapter(mainPageAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
        }
        String recipeId = "3";
        String title = "Gooey Apple Pie";
//        String image = "";
        boolean progress = true;
        int time = 360;
        String servings = "8-12";
        List<Ingredient> ingredients = null;
        List<Step> steps = null;


        //http://stackoverflow.com/questions/26292969/can-i-store-image-files-in-firebase-using-java-api
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        ByteArrayOutputStream byteArrOpStrm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrOpStrm);
        bitmap.recycle();
        byte[] bytes = byteArrOpStrm.toByteArray();
        String image = Base64.encodeToString(bytes, Base64.DEFAULT);
//
//        mConditionRef.setValue(image);
//
//        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//        Bitmap bitmapDecode = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

//        writeNewRecipe(recipeId, title, image, progress, time, servings, ingredients, steps);
        writeNewIngredient(recipeId, "1", "5", "pounds", "apples, peeled, cored, and sliced 1/2 inch thick");
        writeNewIngredient(recipeId, "2", "100-150", "grams", "sugar, plus more for sprinkling");

        writeNewStep(recipeId, "1", "Adjust oven rack to lower middle position and place a heavy rimmed baking sheet on it. Preheat oven to 425F (220C). Toss apple slices with sugar, cornstarch, cinnamon, and lemon juice and zest until well-coated. Let rest for 10 minutes.", null, 10);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void writeNewRecipe(String recipeId, String title, String image, boolean progress, int time, String servings, List<Ingredient> ingredients, List<Step> steps) {
        Recipe recipe = new Recipe(title, image, progress, time, servings, ingredients, steps);

        mRootRef.child("recipes").child(recipeId).setValue(recipe);
    }

    private void writeNewIngredient(String recipeId, String ingredientId, String quantity, String measure, String ingredient) {
        Ingredient ing = new Ingredient(quantity, measure, ingredient);

        mRootRef.child("recipes").child(recipeId).child("ingredients").child(ingredientId).setValue(ing);
    }

    private void writeNewStep(String recipeId, String stepId, String method, String image, int time) {
        Step step = new Step(stepId, method, image, time);

        mRootRef.child("recipes").child(recipeId).child("steps").child(stepId).setValue(step);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        // http://androidforums.com/threads/how-to-change-color-of-menu-item-icon-in-android.916282/
//        Drawable createRecipeIcon = menu.findItem(R.id.action_create_recipe).getIcon();
//        if (createRecipeIcon != null) {
////            createRecipeIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
//        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_recipe:
                Intent intent = new Intent(this, CreateRecipeActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}