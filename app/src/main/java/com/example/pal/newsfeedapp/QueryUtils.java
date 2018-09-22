package com.example.pal.newsfeedapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);
        Log.i("value", String.valueOf(url));
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<News> earthquacke1 = extractNews(jsonResponse);

        return earthquacke1;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {


        }
        return url;


    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // if the URL is null , then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000 /* milliseconds*/);
            urlConnection.setConnectTimeout(15000 /*milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake Json results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        return jsonResponse;


    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamreader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamreader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<News> extractNews(String news) {

        List<News> newsList = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(news);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");


            for (int i = 0; i < results.length(); i++) {
                String pillarName = null;

                JSONObject newsArrayObject = results.getJSONObject(i);
                if (newsArrayObject.has("pillarName")) {
                    pillarName = newsArrayObject.getString("pillarName");
                    Log.i("pillar Name", String.valueOf(pillarName));
                }

                String webTitle = newsArrayObject.getString("webTitle");
                Log.i("web title", webTitle);

                String webUrl = newsArrayObject.getString("webUrl");
                Log.i("webUrl", webUrl);

                String contributer = null;
                if (newsArrayObject.has("contributer")) {
                    contributer = newsArrayObject.getString("contributer");
                    Log.i("contributer", contributer);
                }
                String newsDate = newsArrayObject.getString("webPublicationDate"); //webPublicationDate
                Log.i("webPublicationDate", newsDate);


                newsList.add(new News(pillarName, webTitle, webUrl, contributer, newsDate));


            }


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the NEWS JSON results", e);
        }

        return newsList;
    }

}
