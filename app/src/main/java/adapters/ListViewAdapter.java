package adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
 * Created by Tobias on 09-06-2016.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {
    private Context mContext;
    private List<RecipeViewItem> listData;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<RecipeViewItem> listData) {
        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        mContext = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        RecipeViewItem item = listData.get(position);
        holder.title.setText(item.getTitle());

        Picasso.with(mContext)
                .load(item.getImageResId())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;
        private View container;

        public ListViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView) itemView.findViewById(R.id.ia_item_icon);
            container = itemView.findViewById(R.id.cont_item_root);
        }
    }
}
