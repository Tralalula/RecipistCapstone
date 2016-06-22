package util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tobias on 22-06-2016.
 */
public class FirebaseUtil {
    private static String users = "users";
    private static String recipes = "recipes";

    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user != null) return user.getUid();
        return null;
    }

    public static DatabaseReference getCurrentUserRef() {
        String uId = getCurrentUserId();
        if (uId != null) return getBaseRef().child(users).child(getCurrentUserId());
        return null;
    }

    public static DatabaseReference getRecipesRef() {
        return getBaseRef().child(recipes);
    }

    public static DatabaseReference getUsersRef() {
        return getBaseRef().child(users);
    }
}
