package com.example.ahmet.scoin.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.ahmet.scoin.Fragment.CompareFragment;
import com.example.ahmet.scoin.Fragment.HistoryFragment;
import com.example.ahmet.scoin.Fragment.NowFragment;
import com.example.ahmet.scoin.R;

/**
 * Created by ahmet on 10.12.2017.
 */

public class ScoinFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int currentPosition = -1;
    private Fragment currentFragment;

    public ScoinFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NowFragment();
        } else if (position == 1){
            return new HistoryFragment();
        } else {
            return new CompareFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_now);
        } else if (position == 1) {
            return mContext.getString(R.string.category_history);
        } else {
            return mContext.getString(R.string.category_compare);
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.currentPosition = position;
        if (object instanceof Fragment) {
            this.currentFragment = (Fragment) object;
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
