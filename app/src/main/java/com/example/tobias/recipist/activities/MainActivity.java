package com.example.tobias.recipist.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import adapters.MainPageAdapter;
import data.Ingredients;
import data.Recipe;
import data.Steps;
import fragments.FirebaseListFragment;
import fragments.GalleryViewFragment;
import fragments.RecipesGridFragment;
import fragments.RecipesListFragment;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = mRootRef.child("condition");

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        FragmentPagerAdapter mainPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new RecipesListFragment(),
                    new RecipesGridFragment()
            };

            private final String[] mFragmentNames = new String[]{
                    "List",
                    "Gallery"
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        if (viewPager != null) {
            viewPager.setAdapter(mainPageAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            if (tabLayout != null) {
                tabLayout.setupWithViewPager(viewPager);
            }
        }
//        String recipeId = "3";
//        String title = "Gooey Apple Pie";
////        String image = "";
//        boolean progress = true;
//        int time = 360;
//        String servings = "8-12";
//        List<Ingredient> ingredients = null;
//        List<Step> steps = null;
//
//
//        //http://stackoverflow.com/questions/26292969/can-i-store-image-files-in-firebase-using-java-api

//
//        mConditionRef.setValue(image);
//
//        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//        Bitmap bitmapDecode = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

//        writeNewRecipe(recipeId, title, image, progress, time, servings, ingredients, steps);
//        writeNewIngredient(recipeId, "1", "5", "pounds", "apples, peeled, cored, and sliced 1/2 inch thick");
//        writeNewIngredient(recipeId, "3", "100-150", "grams", "sugar, plus more for sprinkling");
//
//        writeNewStep(recipeId, "2", "Adjust oven rack to lower middle position and place a heavy rimmed baking sheet on it. Preheat oven to 425F (220C). Toss apple slices with sugar, cornstarch, cinnamon, and lemon juice and zest until well-coated. Let rest for 10 minutes.", null, 10);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        ByteArrayOutputStream byteArrOpStrm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrOpStrm);
        bitmap.recycle();
        byte[] bytes = byteArrOpStrm.toByteArray();
        String imageApplePie = Base64.encodeToString(bytes, Base64.DEFAULT);

        ArrayList<Ingredients.Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredients.Ingredient("5 pounds (2.25 kg) apples, peeled, cored, and sliced 1/2 inch thick"));
        ingredients.add(new Ingredients.Ingredient("100-150g sugar, plus more for sprinkling"));
        ingredients.add(new Ingredients.Ingredient("15g cornstarch"));
        ingredients.add(new Ingredients.Ingredient("1/2 tsp ground cinnamon"));
        ingredients.add(new Ingredients.Ingredient("2 tsp fresh juice and 1 tsp grated zest from 1 lemon"));
        ingredients.add(new Ingredients.Ingredient("1 recipe easy pie dough"));
        ingredients.add(new Ingredients.Ingredient("1 egg white"));

        ArrayList<Steps.Step> steps = new ArrayList<>();
        steps.add(new Steps.Step("Adjust oven rack to lower middle position and place a heavy rimmed baking sheet on it. Preheat the oven to 425°F (220°C). Toss apple slices with sugar, cornstarch, cinnamon, and lemon juice and zest until well-coated. Let rest for 10 minutes."));
        steps.add(new Steps.Step("To Cook Filling on the Stovetop: Transfer apples and their juices to a large Dutch oven. Heat over low heat, stirring constantly, until lightly steaming. Cover and continue cooking over lowest heat setting, stirring frequently, using a thermometer to maintain temperature at below 160°F (71°C). Do not allow liquid to come to a boil for first 20 minutes. After 20 minutes, increase heat to medium-high and cook, stirring frequently, until juices thicken enough that a spatula dragged through the bottom of the pot leaves a trail that very slowly closes back up, about 10 minutes. Transfer apples to a rimmed baking sheet, spread out into a single layer, and allow to cool completely, about 1 hour."));
        steps.add(new Steps.Step("To Cook Filling in a Sous-Vide Precision Cooker (see note above): Set precision cooker to 160°F (70°C). Transfer apples and their juices to a vacuum bag and seal. Cook in water bath for 1 hour. Transfer contents to a large Dutch oven and heat over medium-high heat, stirring frequently, until juices thicken enough that a spatula dragged through the bottom of the pot leaves a trail that very slowly closes back up, about 10 minutes. Transfer apples to a rimmed baking sheet, spread out into a single layer, and allow to cool completely, about 1 hour."));
        steps.add(new Steps.Step("Roll one disk of pie dough into a circle roughly 12-inches in diameter. Transfer to a 9-inch pie plate. Add filling, piling it into the pie shell until it all fits. Roll remaining disk of pie dough into a circle roughly 12-inches in diameter. Transfer to top of pie."));
        steps.add(new Steps.Step("Using a pair of kitchen shears, trim the edges of both pie crusts until they overhang the edge of the pie plate by 1/2 inch all the way around. Fold edges of both pie crusts down together, tucking them in between the bottom crust and the pie plate and working your way all the way around the pie plate until everything is well tucked. Use the forefinger on your left hand and the thumb and forefinger on your right hand to crimp the edges. Cut 5 slits in the top with a sharp knife for ventilation."));
        steps.add(new Steps.Step("Use a pastry brush to brush an even coat of lightly beaten egg white all over the top surface of the pie. Sprinkle evenly with a tablespoon of sugar. Transfer pie to sheet tray in the oven and bake until light golden brown, about 20 minutes. Reduce heat to 375°F (190°C) and continue baking until deep golden brown, about 25 minutes longer. Remove from oven and allow to cool at room temperature for at least 4 hours before serving."));
//        writeNewRecipe("Gooey Apple Pie", imageApplePie, true, "6h", "8-12", ingredients, steps);

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void writeNewRecipe(String title, String image, boolean progress, String time,
                               String servings, ArrayList<Ingredients.Ingredient> ingredients, ArrayList<Steps.Step> steps) {
        String key = FirebaseDatabase.getInstance().getReference().child("recipes").push().getKey();
        Recipe recipe = new Recipe(
                title,
                image,
                progress,
                time,
                servings,
                ingredients,
                steps
        );
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

//    private void writeNewRecipe(String recipeId, String title, String image, boolean progress, int time, String servings, List<Ingredient> ingredients, List<Step> steps) {
//        Recipe recipe = new Recipe(title, image, progress, time, servings, ingredients, steps);
//
//        mRootRef.child("recipes").child(recipeId).setValue(recipe);
//    }
//
//    private void writeNewIngredient(String recipeId, String ingredientId, String quantity, String measure, String ingredient) {
//        Ingredient ing = new Ingredient(quantity, measure, ingredient);
//
//        mRootRef.child("recipes").child(recipeId).child("ingredients").child(ingredientId).setValue(ing);
//    }
//
//    private void writeNewStep(String recipeId, String stepId, String method, String image, int time) {
//        Step step = new Step(stepId, method, image, time);
//
//        mRootRef.child("recipes").child(recipeId).child("steps").child(stepId).setValue(step);
//    }

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