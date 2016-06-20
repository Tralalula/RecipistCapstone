package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobias.recipist.R;
import com.squareup.picasso.Picasso;

import data.Recipe;
import util.Utilities;

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

    public void bindToPost(Context context, Recipe recipe) {
        title.setText(recipe.title);
//        thumbnail.setImageBitmap(Utilities.convertBase64ToBitmap(recipe.image));
        Picasso.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/project-2443912486229475189.appspot.com/o/c.jpg?alt=media&token=e3b250f2-ff9d-4279-ba0e-6492d5cade0c")
                .into(thumbnail);

        if (recipe.progress) progress.setText("Completed");
        else progress.setText("In Progress");
        timer.setText(recipe.time);
    }
}
