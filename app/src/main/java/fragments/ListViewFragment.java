package fragments;

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

import adapters.ListViewAdapter;
import data.RecipeViewItem;

/**
 * Created by Tobias on 09-06-2016.
 */
public class ListViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list_view, container, false);

        List<RecipeViewItem> recipeViewItems = getAllItemList();
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListViewAdapter(mRecyclerView.getContext(), recipeViewItems);
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
