package adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobias.recipist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.RecipeViewItem;

/**
 * Created by Tobias on 10-06-2016.
 */
public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.GalleryViewHolder> {
    private LayoutInflater mInflater;
    private List<RecipeViewItem> mListData;
    private Context mContext;

    public GalleryViewAdapter(Context context, List<RecipeViewItem> itemList) {
        mInflater = LayoutInflater.from(context);
        mListData = itemList;
        mContext = context;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layout = mInflater.inflate(R.layout.recipe_gallery_view_item, null);
        } else {
            layout = mInflater.inflate(R.layout.recipe_gallery_landscape_view_item, null);
        }
        return new GalleryViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        RecipeViewItem item = mListData.get(position);

        holder.title.setText(item.getTitle());
        Picasso.with(mContext)
                .load(item.getImageResId())
                .into(holder.thumbnail);
        holder.progress.setText(item.getProgress());
        holder.timer.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public TextView progress;
        public TextView timer;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.recipe_title);
            thumbnail = (ImageView) itemView.findViewById(R.id.recipe_thumbnail);
            progress = (TextView) itemView.findViewById(R.id.recipe_progress);
            timer = (TextView) itemView.findViewById(R.id.recipe_time);
        }
    }
}
