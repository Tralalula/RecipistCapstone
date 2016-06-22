// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.com/2014/03/collection-widget-with-event-handling.html

package widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 22-06-2016.
 */
public class RecipistWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    List<String> mCollections = new ArrayList<>();

    private Context mContext;

    public RecipistWidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    private void initData() {
        mCollections.clear();
        for (int i = 1; i <= 10; i++) {
            mCollections.add("ListView item " + i);
        }
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollections.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, mCollections.get(position));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
