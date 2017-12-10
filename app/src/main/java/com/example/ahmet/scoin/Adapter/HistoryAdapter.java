package com.example.ahmet.scoin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmet.scoin.R;
import com.example.ahmet.scoin.Scoin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmet on 10.12.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {
    private List<Scoin> mScoinData;

    public HistoryAdapter() {}

    public class HistoryAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mScoinDateTextView;
        public final TextView mScoinValueTextView;

        public HistoryAdapterViewHolder(View view) {
            super(view);
            mScoinDateTextView = (TextView) view.findViewById(R.id.tv_scoin_date);
            mScoinValueTextView = (TextView) view.findViewById(R.id.tv_scoin_value);
        }

    }

    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.history_list_item, viewGroup, shouldAttachToParentImmediately);
        return new HistoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapterViewHolder historyAdapterViewHolder, int position) {
        Scoin scoin = mScoinData.get(position);

        Date dateObject = new Date(scoin.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LL yyyy");
        String formattedDate = dateFormat.format(dateObject);

        historyAdapterViewHolder.mScoinDateTextView.setText(formattedDate);
        historyAdapterViewHolder.mScoinValueTextView.setText(String.format("%1$,.2f", scoin.getValue()));
    }

    @Override
    public int getItemCount() {
        if (null == mScoinData) return 0;
        return mScoinData.size();
    }

    public void setScoinData(List<Scoin> scoinData) {
        mScoinData = scoinData;
        notifyDataSetChanged();
    }
}
