package data;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 09-06-2016.
 */
public class GalleryViewDummyData {
    private static final String[] titles = {
            "Apple Pie",
            "Halibut",
            "Pork Chop"
    };

    private static final Integer[] icons = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c
    };

    public static List<GalleryViewItem> getListData() {
        List<GalleryViewItem> data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < titles.length && j < icons.length; j++) {
                GalleryViewItem item = new GalleryViewItem();
                item.setImageResId(icons[j]);
                item.setTitle(titles[j]);
                data.add(item);
            }
        }

        return data;
    }
}
