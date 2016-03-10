
package com.intedemoapp.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.intedemoapp.model.SampleData;

import java.util.List;

/**
 *  Class for loading data using AsyncTaskLoader.
 *
 *  Created by shobharam.piplode on 3/10/2016.
 */
public class DataLoader extends AsyncTaskLoader<List<SampleData>>
{
    private static final String TAG = "DataLoader";
    private final String mUrl;
    private Context mContext;

    public DataLoader(Context context, String url) {
        super(context);
        mContext = context;
        this.mUrl = url;
    }

    @Override
    public List<SampleData> loadInBackground()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

        if (activeInfo != null && activeInfo.isConnected()) {
            try {
                return DataProvider.buildData(mUrl);
            } catch (Exception e) {
                Log.e(TAG, "Failed to fetch data", e);
                return null;
            }

        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

}
