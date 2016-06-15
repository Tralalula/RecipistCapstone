package data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Tobias on 15-06-2016.
 */
@IgnoreExtraProperties
public class Step {
    public String id;
    public String method;
    public String image;
    public int time;

    public Step() {

    }

    public Step(String id, String method, String image, int time) {
        this.id = id;
        this.method = method;
        this.image = image;
        this.time = time;
    }
}
