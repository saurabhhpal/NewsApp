package com.example.pal.newsfeedapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=46c6d625-a89a-42fd-8004-19be7e5ceda5";
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.i(" NEWS_REQUEST_URL", NEWS_REQUEST_URL);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> result = QueryUtils.fetchNewsData(mUrl);
        Log.i("load in background ", result.toString());
        return result;
    }
}
