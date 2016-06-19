package viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobias.recipist.R;

import data.Recipe;

/**
 * Created by Tobias on 20-06-2016.
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView thumbnail;
    public TextView progress;
    public TextView timer;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.recipe_title);
        thumbnail = (ImageView) itemView.findViewById(R.id.recipe_thumbnail);
        progress = (TextView) itemView.findViewById(R.id.recipe_progress);
        timer = (TextView) itemView.findViewById(R.id.recipe_time);
    }

    public void bindToPost(Recipe recipe, View.OnClickListener clickListener) {
        title.setText(recipe.title);
    }
}
