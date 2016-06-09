package fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobias.recipist.R;

import adapters.GalleryViewAdapter;
import data.GalleryViewDummyData;

/**
 * Created by Tobias on 09-06-2016.
 */
public class GalleryViewFragment2 extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private GalleryViewAdapter mGalleryViewAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment2_gallery_view, container, false);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mGalleryViewAdapter = new GalleryViewAdapter(GalleryViewDummyData.getListData(), mRecyclerView.getContext());
        mRecyclerView.setAdapter(mGalleryViewAdapter);

        return mRecyclerView;
    }

}
