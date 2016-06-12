package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.List;

import adapters.RecipeViewAdapter;
import data.RecipeViewItem;
import util.ItemDivider;

/**
 * Created by Tobias on 09-06-2016.
 */
public class ListViewFragment extends Fragment {
    public static int FRAGMENT_ID = 11;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list_view, container, false);
        Context recyclerViewContext = mRecyclerView.getContext();

        List<RecipeViewItem> recipeViewItems = getAllItemList();
        mLayoutManager = new LinearLayoutManager(recyclerViewContext);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new ItemDivider(recyclerViewContext));

        mAdapter = new RecipeViewAdapter(recyclerViewContext, recipeViewItems, FRAGMENT_ID);
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
