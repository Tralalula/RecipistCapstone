package fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.List;

import adapters.GalleryViewAdapter;
import data.RecipeViewItem;

/**
 * Created by Tobias on 10-06-2016.
 */
public class GalleryViewFragment extends Fragment {
    int PORTRAIT_NUM_OF_RECIPES = 2;
    int LANDSCAPE_NUM_OF_RECIPES = 3;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_gallery_view, container, false);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.windowBackgroundDark));

        List<RecipeViewItem> recipeViewItems = getAllItemList();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), PORTRAIT_NUM_OF_RECIPES);
        } else {
            mLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), LANDSCAPE_NUM_OF_RECIPES);
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GalleryViewAdapter(mRecyclerView.getContext(), recipeViewItems);
        mRecyclerView.setAdapter(mAdapter);

        return mRecyclerView;
    }

    private List<RecipeViewItem> getAllItemList() {
        List<RecipeViewItem> allItems = new ArrayList<>();
        allItems.add(new RecipeViewItem("Apple Pie", R.drawable.a, "Completed", "45m"));
        allItems.add(new RecipeViewItem("Halibut", R.drawable.b, "In Progress", "1h30m"));
        allItems.add(new RecipeViewItem("Pork Chop", R.drawable.c, "Completed", "3h30m"));
        allItems.add(new RecipeViewItem("Apple Pie", R.drawable.a, "Completed", "45m"));
        allItems.add(new RecipeViewItem("Halibut", R.drawable.b, "In Progress", "1h30m"));
        allItems.add(new RecipeViewItem("Pork Chop", R.drawable.c, "Completed", "3h30m"));
        return allItems;
    }
}