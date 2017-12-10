package com.example.ahmet.scoin.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.ahmet.scoin.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmet on 10.12.2017.
 */

public class RateLoader extends AsyncTaskLoader<List<Double>> {

    private static final String LOG_TAG = RateLoader.class.getSimpleName();

    private String mUrl;

    public RateLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Double> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        URL url = Utility.createUrl(mUrl);

        String jsonResponse = null;
        try {
            jsonResponse = Utility.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractRatesFromJson(jsonResponse);
    }





    private List<Double> extractRatesFromJson(String ratesJSON) {
        if (TextUtils.isEmpty(ratesJSON)) {
            return null;
        }

        List<Double> rates = new ArrayList<>();

        try {
            JSONObject baseObject = new JSONObject(ratesJSON);
            JSONObject ratesObject = baseObject.getJSONObject("rates");

            rates.add(ratesObject.getDouble("EUR"));
            rates.add(ratesObject.getDouble("TRY"));

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the rates JSON results", e);
        }

        return rates;
    }
}
