// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.com/2014/03/collection-widget-with-event-handling.html

package widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.tobias.recipist.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import data.Recipe;
import util.FirebaseUtil;

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
//        System.out.println("initData() start");
        mCollections.clear();
        DatabaseReference reference = FirebaseUtil.getBaseRef().child("recipes");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Recipe recipe = dataSnapshot.getValue(Recipe.class);
//                System.out.println("recipe = " + recipe);
//                System.out.println("recipe.title = " + recipe.title);
//                System.out.println("dataSnapshot.hasChildren() = " + dataSnapshot.hasChildren());
//                System.out.println("dataSnapshot.getChildrenCount() = " + dataSnapshot.getChildrenCount());
//                System.out.println();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
//                    System.out.println("recipe1 = " + recipe1);
//                    System.out.println("recipe1.title = " + recipe1.title);
//                    System.out.println("dataSnapshot1.hasChildren() = " + dataSnapshot1.hasChildren());
//                    System.out.println("dataSnapshot1.getChildrenCount() = " + dataSnapshot1.getChildrenCount());
                    mCollections.add(recipe.title);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
//
//        for (int i = 1; i <= 10; i++) {
//            mCollections.add("ListView item " + i);
//            System.out.println("ListView item " + i);
//        }
//        System.out.println("initData() end");
    }

    @Override
    public void onCreate() {
//        System.out.println("onCreate() start");
        initData();
//        System.out.println("initData() end");
    }

    @Override
    public void onDataSetChanged() {
//        System.out.println("onDataSetChanged() start");
        initData();
//        System.out.println("onDataSetChanged() end");
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