package data;

/**
 * Created by Tobias on 11-06-2016.
 */
public class RecipeViewItem {
    private String title;
    private int imageResId;
    private String progress;
    private String time;

    public RecipeViewItem(String title, int imageResId, String progress, String time) {
        this.title = title;
        this.imageResId = imageResId;
        this.progress = progress;
        this.time = time;
    }

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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
