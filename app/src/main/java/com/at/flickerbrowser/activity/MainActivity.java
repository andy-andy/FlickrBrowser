package com.at.flickerbrowser.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.at.flickerbrowser.R;
import com.at.flickerbrowser.adapters.FlickrPagerAdapter;
import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.repo.Resource;
import com.at.flickerbrowser.repo.Status;
import com.at.flickerbrowser.utils.NetworkUtil;
import com.at.flickerbrowser.viewmodels.MainViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FlickrPagerAdapter mFlickrPagerAdapter;

    private static final int SPLASH_DELAY_MILLIS = 1000;

    @Inject
    MainViewModel mViewModel;

    @Inject
    NetworkUtil mNetworkUtil;

    @BindView(R.id.splashProgressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.main_root_relative_layout)
    View mLayout;

    @BindView(R.id.mainSwipeContainer)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        new Handler().postDelayed(this::subscribeToModel, SPLASH_DELAY_MILLIS);

        if (mNetworkUtil.isConnected()) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            mProgressBar.getIndeterminateDrawable().setColorFilter(
                    Color.BLUE,
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            Toast.makeText(
                    this,
                    getString(R.string.error_no_internet_connection),
                    Toast.LENGTH_LONG).show();
        }

        //Set the pager with an adapter
        mPager = findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(3); // Helps to keep fragment alive, otherwise I will have to load again images
        mFlickrPagerAdapter = new FlickrPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mFlickrPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        subscribeToModel();
                    }
                }
        );


        //Bind the title indicator to the adapter
        CirclePageIndicator titleIndicator = findViewById(R.id.circle_indicator);
        titleIndicator.setViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    private void subscribeToModel() {
        mViewModel.getFlickrFeed().observe(this, new android.arch.lifecycle.Observer<Resource<FlickrResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<FlickrResponse> flickrResponseResource) {
                if (flickrResponseResource != null) {
                    if (flickrResponseResource.getStatus() == Status.LOADING) {
                        Log.i("blah", "Loading...");
                    } else if (flickrResponseResource.getStatus() == Status.ERROR) {
                        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                        Log.i("blah", "Error: " + flickrResponseResource.getMessage());
                    } else if (flickrResponseResource.getStatus() == Status.SUCCESS) {
                        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mFlickrPagerAdapter.setData(flickrResponseResource.getData());
                        mFlickrPagerAdapter.notifyDataSetChanged();
                        Log.i("blah", "Success: " + flickrResponseResource.getData());
                    }
                }
            }
        });
    }
}
