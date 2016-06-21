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
    private TextView mRecipeTitle;
    private ImageView mRecipeThumbnail;
    private TextView mRecipeNoThumbnail;
    private TextView mRecipeProgress;
    private TextView mRecipeTime;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        mRecipeTitle = (TextView) itemView.findViewById(R.id.recipe_title);
        mRecipeThumbnail = (ImageView) itemView.findViewById(R.id.recipe_thumbnail);
        mRecipeNoThumbnail = (TextView) itemView.findViewById(R.id.recipe_nothumbnail);
        mRecipeProgress = (TextView) itemView.findViewById(R.id.recipe_progress);
        mRecipeTime = (TextView) itemView.findViewById(R.id.recipe_time);
    }

    public void bindToPost(Context context, Recipe recipe) {
        String notSpecified = context.getString(R.string.not_specified);

        String image = recipe.image;
        String title = recipe.title;
        String time = recipe.time;

        boolean progress = recipe.progress;
        String progressText;

        if (Utilities.isNullOrEmpty(image)) {
            mRecipeThumbnail.setVisibility(View.GONE);
            mRecipeNoThumbnail.setVisibility(View.VISIBLE);
        } else {
            mRecipeThumbnail.setVisibility(View.VISIBLE);
            mRecipeNoThumbnail.setVisibility(View.GONE);
            //        thumbnail.setImageBitmap(Utilities.convertBase64ToBitmap(recipe.image));
            Picasso.with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/project-2443912486229475189.appspot.com/o/c.jpg?alt=media&token=e3b250f2-ff9d-4279-ba0e-6492d5cade0c")
                    .into(mRecipeThumbnail);
        }

        if (Utilities.isNullOrEmpty(title)) title = notSpecified;
        if (Utilities.isNullOrEmpty(time)) time = notSpecified;

        if (progress) progressText = context.getString(R.string.view_recipe_progress_true);
        else progressText = context.getString(R.string.view_recipe_progress_false);

        mRecipeTitle.setText(title);
        mRecipeTime.setText(time);
        mRecipeProgress.setText(progressText);
    }
}
