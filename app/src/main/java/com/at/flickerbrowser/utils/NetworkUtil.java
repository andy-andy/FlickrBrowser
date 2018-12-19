package com.at.flickerbrowser.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.at.flickerbrowser.App;

import javax.inject.Inject;

public class NetworkUtil {

    private App mAppContext;

    @Inject
    public NetworkUtil(App app) {
        mAppContext = app;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mAppContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
