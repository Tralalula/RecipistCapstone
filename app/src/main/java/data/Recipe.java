package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 15-06-2016.
 */
@IgnoreExtraProperties
public class Recipe implements Parcelable {
    public String title;
    public String image;
    public boolean progress;
    public String time;
    public String servings;
    public ArrayList<Ingredients.Ingredient> ingredients;
    public ArrayList<Steps.Step> steps;

    public Recipe() {
    }

    public Recipe(String title, String image, boolean progress, String time, String servings, ArrayList<Ingredients.Ingredient> ingredients, ArrayList<Steps.Step> steps) {
        this.title = title;
        this.image = image;
        this.progress = progress;
        this.time = time;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        image = in.readString();
        progress = in.readByte() != 0;
        time = in.readString();
        servings = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.Ingredient.CREATOR);
        steps = in.createTypedArrayList(Steps.Step.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeByte((byte) (progress ? 1 : 0));
        dest.writeString(time);
        dest.writeString(servings);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
