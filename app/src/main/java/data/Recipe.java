package data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Tobias on 15-06-2016.
 */
@IgnoreExtraProperties
public class Recipe {
    public String title;
    public String image;
    public boolean progress;
    public int time;
    public String servings;
    public List<Ingredients.Ingredient> ingredients;
    public List<Step> steps;

    public Recipe() {
    }

    public Recipe(String title, String image, boolean progress, int time, String servings, List<Ingredients.Ingredient> ingredients, List<Step> steps) {
        this.title = title;
        this.image = image;
        this.progress = progress;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
