package com.at.flickerbrowser.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.at.flickerbrowser.R;
import com.at.flickerbrowser.activity.MainActivity;
import com.at.flickerbrowser.models.FlickrResponse;
import com.at.flickerbrowser.repo.Resource;
import com.at.flickerbrowser.repo.Status;
import com.at.flickerbrowser.viewmodels.MainViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends Fragment {

    @Inject
    MainViewModel mViewModel;

    @BindView(R.id.refresh_btn)
    Button mRefreshBtn;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (!(getActivity() instanceof MainActivity)) {
            throw new IllegalStateException("Fragment can only be called from MainActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.getFlickrFeed().observe(this, new android.arch.lifecycle.Observer<Resource<FlickrResponse>>() {
            @Override
            public void onChanged(@Nullable Resource<FlickrResponse> flickrResponseResource) {
                if (flickrResponseResource != null) {
                    if (flickrResponseResource.getStatus() == Status.LOADING) {
                        Log.i("blah", "Loading...");
                    } else if (flickrResponseResource.getStatus() == Status.ERROR) {
                        Log.i("blah", "Error" + flickrResponseResource.getMessage());
                    } else if (flickrResponseResource.getStatus() == Status.SUCCESS) {
                        Log.i("blah", "Error" + flickrResponseResource.getData());
                    }
                }
            }
        });
    }
}
