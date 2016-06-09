package data;

import com.example.tobias.recipist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 09-06-2016.
 */
public class ListViewDummyData {
    private static final String[] titles = {
            "Nothingness cannot be defined",
            "The softest thing cannot be snapped",
            "be like water, my friend."
    };

    private static final Integer[] icons = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c
    };

    public static List<ListViewItem> getListData() {
        List<ListViewItem> data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < titles.length && j < icons.length; j++) {
                ListViewItem item = new ListViewItem();
                item.setImageResId(icons[j]);
                item.setTitle(titles[j]);
                data.add(item);
            }
        }

        return data;
    }
}