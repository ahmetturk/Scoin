package com.example.ahmet.scoin.Fragment;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmet.scoin.Loader.NowLoader;
import com.example.ahmet.scoin.R;
import com.example.ahmet.scoin.Scoin;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment implements LoaderManager.LoaderCallbacks<Scoin> {

    private static final int NOW_LOADER_ID = 1;

    private static final String NOW_URL = "https://devakademi.sahibinden.com/ticker";

    private TextView mValueTextView;
    private TextView mDateTextView;

    public NowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_now, container, false);

        mValueTextView = (TextView) parentView.findViewById(R.id.value_text_view);
        mDateTextView = (TextView) parentView.findViewById(R.id.date_text_view);

        LoaderManager loaderManager = getActivity().getLoaderManager();

        loaderManager.initLoader(NOW_LOADER_ID, null, this);

        return parentView;
    }

    @Override
    public Loader<Scoin> onCreateLoader(int i, Bundle bundle) {
        return new NowLoader(getActivity(), NOW_URL);
    }

    @Override
    public void onLoadFinished(Loader<Scoin> loader, Scoin s) {
        mValueTextView.setText(String.format("%1$,.2f$", s.getValue()));

        Date dateObject = new Date(s.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd LLL yyyy");
        String formattedDate = dateFormat.format(dateObject);

        mDateTextView.setText(formattedDate);
    }

    @Override
    public void onLoaderReset(Loader<Scoin> loader) {

    }
}
