package data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Tobias on 15-06-2016.
 */
@IgnoreExtraProperties
public class Ingredient {
    public String quantity;
    public String measure;
    public String ingredient;

    public Ingredient() {
    }

    public Ingredient(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
