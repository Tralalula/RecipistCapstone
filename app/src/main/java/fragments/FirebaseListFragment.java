package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobias.recipist.R;
import com.example.tobias.recipist.activities.ViewRecipeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import data.Recipe;
import viewholders.RecipeViewHolder;

/**
 * Created by Tobias on 20-06-2016.
 */
public abstract class FirebaseListFragment extends Fragment {
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Recipe, RecipeViewHolder> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rec_list);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query recipesQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Recipe, RecipeViewHolder>(Recipe.class,
                R.layout.recipe_list_view_item, RecipeViewHolder.class, recipesQuery) {
            @Override
            protected void populateViewHolder(RecipeViewHolder viewHolder, Recipe model, int position) {
                final DatabaseReference recipeRef = getRef(position);

                final String recipeKey = recipeRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                        intent.putExtra("RECIPE KEY", recipeKey);
                        startActivity(intent);
                    }
                });

                viewHolder.bindToPost(model, null);
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
