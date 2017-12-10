package com.example.ahmet.scoin.Fragment;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmet.scoin.Adapter.HistoryAdapter;
import com.example.ahmet.scoin.Loader.HistoryLoader;
import com.example.ahmet.scoin.R;
import com.example.ahmet.scoin.Scoin;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Scoin>> {

    private static final int HISTORY_LOADER_ID = 2;

    private static final String HISTORY_URL = "https://devakademi.sahibinden.com/history";

    private RecyclerView mRecyclerView;
    private HistoryAdapter historyAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_history, container, false);

        mRecyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview_history);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        historyAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(historyAdapter);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                layoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(HISTORY_LOADER_ID, null, this);

        return parentView;
    }

    @Override
    public Loader<List<Scoin>> onCreateLoader(int i, Bundle bundle) {
        return new HistoryLoader(getActivity(), HISTORY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Scoin>> loader, List<Scoin> scoinList) {
        String pref = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString
                (getString(R.string.pref_history_key), "");

        if (pref.equals(getString(R.string.pref_present_to_past))) {
            Collections.reverse(scoinList);
        }

        historyAdapter.setScoinData(scoinList);
    }

    @Override
    public void onLoaderReset(Loader<List<Scoin>> loader) {

    }

    public void refreshLoader(){
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.restartLoader(HISTORY_LOADER_ID, null, this);
    }
}
