package com.example.pal.newsfeedapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class NewsAdaptor extends ArrayAdapter<News> {
    private static final String LOCATION_SEPARATOR = " of ";


    public NewsAdaptor(Context context, List<News> newsList) {

        super(context, 0, newsList);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }
        // Get the  object located at this position in the list.
        News currentNews = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView categoryTextView = listItemView.findViewById(R.id.category);
        String category = currentNews.getCategory();
        categoryTextView.setText(category);


        TextView titleTextView = listItemView.findViewById(R.id.news_title);
        String title = currentNews.getNewsTitle();
        titleTextView.setText(title);


        TextView datTextView = listItemView.findViewById(R.id.news_date);
        String dateformate = formatDate(currentNews.getDate());
        Log.i("date", currentNews.getDate());
        datTextView.setText(dateformate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatime(currentNews.getDate());
        timeView.setText(formattedTime);
        return listItemView;

    }

    private String formatDate(String date) {
        Date date1 = null;

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Log.i("Date before pars", date);

        try {
            date1 = input.parse(date);
            Log.i("date after is ", output.format(date1));
        } catch (ParseException re) {
            re.printStackTrace();
        }

        String dateToDisplay = output.format(date1);
        return dateToDisplay;
    }

    private String formatime(String date) {
        Date date1 = null;

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outptdateFormater = new SimpleDateFormat("hh:mm:ss");
        try {
            date1 = input.parse(date);
            Log.i("time is ", outptdateFormater.format(date1));
        } catch (ParseException r) {
            r.printStackTrace();
        }
        return outptdateFormater.format(date1);

    }


}


