package fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobias.recipist.R;
import com.example.tobias.recipist.activities.CreateRecipeActivity;
import com.example.tobias.recipist.activities.ViewRecipeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import data.Recipe;
import util.FirebaseUtil;
import util.Utilities;
import viewholders.RecipeViewHolder;

/**
 * Created by Tobias on 20-06-2016.
 */
public abstract class FirebaseGridFragment extends Fragment {
    private int PORTRAIT_NUM_OF_RECIPES = 2;
    private int LANDSCAPE_NUM_OF_RECIPES = 3;


    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Recipe, RecipeViewHolder> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_gallery_view, container, false);

        mDatabase = FirebaseUtil.getBaseRef();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setBackgroundColor(getResources().getColor(R.color.windowBackgroundDark));

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int layout;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(getActivity(), PORTRAIT_NUM_OF_RECIPES);
            layout = R.layout.recipe_gallery_view_item;
        } else {
            mLayoutManager = new GridLayoutManager(getActivity(), LANDSCAPE_NUM_OF_RECIPES);
            layout = R.layout.recipe_gallery_landscape_view_item;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        Query recipesQuery = getQuery(mDatabase);


        mAdapter = new FirebaseRecyclerAdapter<Recipe, RecipeViewHolder>(Recipe.class,
                layout, RecipeViewHolder.class, recipesQuery) {
            @Override
            protected void populateViewHolder(RecipeViewHolder viewHolder, Recipe model, int position) {
                final DatabaseReference recipeRef = getRef(position);

                final String recipeKey = recipeRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                        intent.putExtra(Utilities.VIEW_RECIPE_ACTIVITY_RECIPE_KEY, recipeKey);
                        startActivity(intent);
                    }
                });

                viewHolder.bindToPost(mRecyclerView.getContext(), model);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);
}
