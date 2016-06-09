package adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobias.recipist.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import data.GalleryViewItem;
import data.ListViewItem;

/**
 * Created by Tobias on 09-06-2016.
 */
public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<GalleryViewItem> listData;
    private LayoutInflater inflater;

    public GalleryViewAdapter(List<GalleryViewItem> listData, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        mContext = context;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gallery_view_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GalleryViewItem item = listData.get(position);
        holder.title.setText(item.getTitle());

        Picasso.with(mContext)
                .load(item.getImageResId())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private View container;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView) itemView.findViewById(R.id.ia_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);
        }
    }
}
