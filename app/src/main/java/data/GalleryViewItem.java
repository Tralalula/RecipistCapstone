package data;

/**
 * Created by Tobias on 09-06-2016.
 */
public class GalleryViewItem {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    private String title;
    private int imageResId;
}