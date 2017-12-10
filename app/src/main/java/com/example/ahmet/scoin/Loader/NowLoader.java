package com.example.ahmet.scoin.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.ahmet.scoin.Scoin;
import com.example.ahmet.scoin.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ahmet on 10.12.2017.
 */

public class NowLoader extends AsyncTaskLoader<Scoin> {

    private static final String LOG_TAG = NowLoader.class.getSimpleName();

    private String mUrl;

    public NowLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Scoin loadInBackground() {
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

        return extractScoinFromJson(jsonResponse);
    }





    private Scoin extractScoinFromJson(String scoinJSON) {
        if (TextUtils.isEmpty(scoinJSON)) {
            return null;
        }

        Scoin scoin = new Scoin();

        try {
            JSONObject baseJsonResponse = new JSONObject(scoinJSON);

            long date = baseJsonResponse.getLong("date");
            double value = baseJsonResponse.getDouble("value");

            scoin.setDate(date);
            scoin.setValue(value);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the ticker JSON results", e);
        }

        return scoin;
    }
}
