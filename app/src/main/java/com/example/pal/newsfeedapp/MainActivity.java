package com.example.pal.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    Context context;
    private View loadingBar;
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?api-key=46c6d625-a89a-42fd-8004-19be7e5ceda5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = findViewById(R.id.loading);
        getSupportLoaderManager().initLoader(1, null, this);

        View emptyView = getLayoutInflater().inflate(R.layout.emplty_list, null);
        ListView newsListView = findViewById(R.id.list);
        ((ViewGroup) newsListView.getParent()).addView(emptyView);
        newsListView.setEmptyView(emptyView);
        TextView textView = findViewById(R.id.empty_tv);

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
        } else {
            // notify user you re not onlinee
            textView.setText(getResources().getString(R.string.no_connection_available));

            Toast.makeText(this, getResources().getString(R.string.no_connection_available), Toast.LENGTH_LONG).show();
        }
    }


    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String page = sharedPreferences.getString(getString(R.string.settings_page_key), getString(R.string.settings_page_default));
        String category = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("page", page);
        uriBuilder.appendQueryParameter("q", category);

        Log.i("URI", uriBuilder.toString());

        return new NewsLoader(MainActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsData) {
        updateUi(newsData);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        updateUi(null);
    }


    private void updateUi(List<News> newsList) {

        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        loadingBar.setVisibility(View.GONE);
        earthquakeListView.setVisibility(View.VISIBLE);

        final NewsAdaptor adapter = new NewsAdaptor(this, newsList);


        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getNewsUrl());
                Intent webSite = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(webSite);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
