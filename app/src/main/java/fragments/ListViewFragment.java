package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.tobias.recipist.R;

import adapters.ImageAdapter;
import adapters.ListViewAdapter;
import data.ListViewDummyData;

/**
 * Created by Tobias on 09-06-2016.
 */
public class ListViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ListViewAdapter mListViewAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mAdapter = new ListViewAdapter();
        mListViewAdapter = new ListViewAdapter(ListViewDummyData.getListData(), mRecyclerView.getContext());
        mRecyclerView.setAdapter(mListViewAdapter);

        return mRecyclerView;
    }
}
