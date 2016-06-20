package fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Tobias on 20-06-2016.
 */
public class RecipesGridFragment extends FirebaseGridFragment {
    public RecipesGridFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentRecipesQuery = databaseReference.child("recipes");
        return recentRecipesQuery;
    }
}