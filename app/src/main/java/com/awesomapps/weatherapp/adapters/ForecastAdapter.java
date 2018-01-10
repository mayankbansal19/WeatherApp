package com.awesomapps.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomapps.weatherapp.data.HourlyDataPoint;
import com.awesomapps.weatherapp.data.ViewHolderDaily;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import weatherapp.awesomeapps.com.weatherapp.R;

/**
 * Created by Mayank on 7/16/2017.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ViewHolderDaily> {

    List<HourlyDataPoint> list = Collections.emptyList();
    Context context;
    int expandedPosition=-1;

    public ForecastAdapter(List<HourlyDataPoint> list,Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolderDaily onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_cardrow, parent, false);
        ViewHolderDaily holder = new ViewHolderDaily(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderDaily holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.temp.setText(""+list.get(position).temperature+"\u2103");//"\u2109"For farenheit
        holder.time.setText(getTime(list.get(position).time));
        switch(list.get(position).icon)
        {
            case "cloudy":
                holder.imageView.setImageResource(R.drawable.cloudy);
                break;
            case "partly-cloudy-night":
                holder.imageView.setImageResource(R.drawable.partly_cloudy_night);
                break;
            case "rain":
                holder.imageView.setImageResource(R.drawable.rain);
                break;
            case "partly-cloudy-day":
                holder.imageView.setImageResource(R.drawable.partly_cloudy_day);
                break;
            case "clear-day":
                holder.imageView.setImageResource(R.drawable.clear_day);
                break;
        }

        if (holder.humidity.getVisibility() == View.VISIBLE) {
            holder.humidity.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, HourlyDataPoint data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(HourlyDataPoint data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    String getTime(long seconds)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(new Date(seconds*1000));
    }


}
