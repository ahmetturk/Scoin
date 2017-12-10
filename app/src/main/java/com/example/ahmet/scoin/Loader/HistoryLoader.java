package com.example.ahmet.scoin.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.ahmet.scoin.Scoin;
import com.example.ahmet.scoin.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmet on 10.12.2017.
 */

public class HistoryLoader extends AsyncTaskLoader<List<Scoin>> {
    private static final String LOG_TAG = NowLoader.class.getSimpleName();

    private String mUrl;

    public HistoryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Scoin> loadInBackground() {
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

        return extractScoinListFromJson(jsonResponse);
    }

    private List<Scoin> extractScoinListFromJson(String scoinJSON) {
        if (TextUtils.isEmpty(scoinJSON)) {
            return null;
        }

        List<Scoin> scoinList = new ArrayList<>();

        try {
            JSONArray scoinArray = new JSONArray(scoinJSON);
            for(int i = 0; i < scoinArray.length(); i++){
                Scoin scoin = new Scoin();

                JSONObject jsonObject = scoinArray.getJSONObject(i);
                long date = jsonObject.getLong("date");
                double value = jsonObject.getDouble("value");

                scoin.setDate(date);
                scoin.setValue(value);

                scoinList.add(scoin);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return scoinList;
    }
}
