package com.at.flickerbrowser.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.flickerbrowser.R;
import com.at.flickerbrowser.activity.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends Fragment {

    @BindView(R.id.screen_image)
    ImageView mScreenImage;

    private String mImageUrl;

    @BindView(R.id.screen_title)
    TextView mScreenTitle;

    private String mImageTitle;

    private static String IMAGE_TITLE = "imageTitle";
    private static String IMAGE_URL = "imageUrl";

    public static MainFragment newInstance(String imageTitle, String imageUrl) {

        final MainFragment mainFragment = new MainFragment();

        final Bundle args = new Bundle();
        args.putString(IMAGE_TITLE, imageTitle);
        args.putString(IMAGE_URL, imageUrl);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mImageTitle = arguments.getString(IMAGE_TITLE);
            mImageUrl = arguments.getString(IMAGE_URL);
        }
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

        // Populate fields with info!
        mScreenTitle.setText(mImageTitle);

        // Call Glide to load image
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(mImageUrl).apply(options).into(mScreenImage);
    }
}
