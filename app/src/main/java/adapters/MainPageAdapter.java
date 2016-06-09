package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.GalleryViewFragment2;
import fragments.ListViewFragment;

/**
 * Created by Tobias on 09-06-2016.
 */
public class MainPageAdapter extends FragmentStatePagerAdapter {
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GalleryViewFragment2();
            case 1:
                return new ListViewFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Gallery";
            case 1:
                return "List";
            default:
                return null;
        }
    }
}
