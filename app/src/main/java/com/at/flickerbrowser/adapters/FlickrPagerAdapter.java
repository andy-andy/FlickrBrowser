package com.at.flickerbrowser.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.models.Item;
import com.at.flickerbrowser.ui.main.MainFragment;

import java.util.List;

import javax.inject.Inject;

public class FlickrPagerAdapter extends FragmentStatePagerAdapter {

    private List<Item> mDataItems;

    @Inject
    public FlickrPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(FlickrResponse data) {
        mDataItems = data.getItems();
        Log.i("blah", Integer.toString(data.getItems().size()));
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(mDataItems.get(position).getDescription(), mDataItems.get(position).getMedia().getM());
    }

    @Override
    public int getCount() {
        return mDataItems == null ? 0 : mDataItems.size();
    }
}
