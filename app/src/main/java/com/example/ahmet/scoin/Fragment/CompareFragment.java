package com.example.ahmet.scoin.Fragment;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ahmet.scoin.Loader.NowLoader;
import com.example.ahmet.scoin.Loader.RateLoader;
import com.example.ahmet.scoin.R;
import com.example.ahmet.scoin.Scoin;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompareFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static final int NOW_LOADER_ID = 3;
    private static final int RATE_LOADER_ID = 4;

    private static final String NOW_URL = "https://devakademi.sahibinden.com/ticker";
    private static final String RATE_URL = "https://api.fixer.io/latest?base=USD";

    Scoin mNowScoin;
    List<Double> mRates;
    LinearLayout mLinearLayout;
    TextView mDollarTextView;
    TextView mEuroTextView;
    TextView mLiraTextView;
    EditText mEditText;
    Button mCompareButton;

    public CompareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_compare, container, false);

        mLinearLayout = (LinearLayout) parentView.findViewById(R.id.compare_linear_layout);
        mDollarTextView = (TextView) parentView.findViewById(R.id.dollar_text_view);
        mEuroTextView = (TextView) parentView.findViewById(R.id.euro_text_view);
        mLiraTextView = (TextView) parentView.findViewById(R.id.lira_text_view);
        mEditText = (EditText) parentView.findViewById(R.id.compare_edit_text);
        mCompareButton = (Button) parentView.findViewById(R.id.compare_button);

        mCompareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double inputValue;
                if(TextUtils.isEmpty(mEditText.getText().toString())){
                    return;
                } else {
                    try {
                        inputValue = Double.parseDouble(mEditText.getText().toString()) * mNowScoin.getValue();
                    } catch (Exception e) {
                        return;
                    }
                    String dollarValue = String.format("%1$,.2f", inputValue);
                    String euroValue = String.format("%1$,.2f", inputValue * mRates.get(0));
                    String liraValue = String.format("%1$,.2f", inputValue * mRates.get(1));

                    mDollarTextView.setText(dollarValue);
                    mEuroTextView.setText(euroValue);
                    mLiraTextView.setText(liraValue);
                }
            }
        });

        LoaderManager loaderManager = getActivity().getLoaderManager();

        loaderManager.initLoader(NOW_LOADER_ID, null, this);
        loaderManager.initLoader(RATE_LOADER_ID, null, this);

        return parentView;
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        if(i == NOW_LOADER_ID) {
            return new NowLoader(getActivity(), NOW_URL);
        } else {
            return new RateLoader(getActivity(), RATE_URL);
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object object) {
        if (loader.getId() == NOW_LOADER_ID) {
            mNowScoin = (Scoin) object;
        } else if (loader.getId() == RATE_LOADER_ID) {
            mRates = (List<Double>) object;
        }

        if (!getLoaderManager().hasRunningLoaders()) {
            showRates();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void showRates() {
        mLinearLayout.setVisibility(View.VISIBLE);
    }
}
