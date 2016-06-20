package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobias.recipist.R;
import com.example.tobias.recipist.activities.CreateRecipeActivity;
import com.example.tobias.recipist.activities.ViewRecipeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.RecipeViewItem;
import fragments.GalleryViewFragment;

/**
 * Created by Tobias on 10-06-2016.
 */
public class RecipeViewAdapter extends RecyclerView.Adapter<RecipeViewAdapter.RecipeViewHolder> {
    private LayoutInflater mInflater;
    private List<RecipeViewItem> mListData;
    private Context mContext;
    private int mFragmentId;

    public RecipeViewAdapter(Context context, List<RecipeViewItem> itemList, int fragmentId) {
        mInflater = LayoutInflater.from(context);
        mListData = itemList;
        mContext = context;
        mFragmentId = fragmentId;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        if (mFragmentId == GalleryViewFragment.FRAGMENT_ID) {
            if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                layout = mInflater.inflate(R.layout.recipe_gallery_view_item, null);
            } else {
                layout = mInflater.inflate(R.layout.recipe_gallery_landscape_view_item, null);
            }
        } else {
            layout = mInflater.inflate(R.layout.recipe_list_view_item, parent, false);
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewRecipeActivity.class);
                mContext.startActivity(intent);
            }
        });

        return new RecipeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RecipeViewItem item = mListData.get(position);

        holder.title.setText(item.getTitle());
        Picasso.with(mContext)
                .load(item.getImageResId())
                .into(holder.thumbnail);

//        Picasso.with(mContext).lo
        holder.progress.setText(item.getProgress());
        holder.timer.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

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
    }
}
