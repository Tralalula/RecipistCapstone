package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.tobias.recipist.R;

import adapters.ImageAdapter;

/**
 * Created by Tobias on 09-06-2016.
 */
public class GalleryViewFragment extends Fragment {
    private ImageAdapter mImageAdapter;
    private boolean mRestoredState;

    private Integer[] mThumbIds = {R.drawable.a, R.drawable.b, R.drawable.c};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mRestoredState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery_view, container, false);

        mImageAdapter = new ImageAdapter(getActivity());

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(mImageAdapter);

        return rootView;
    }
}
