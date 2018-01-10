package com.awesomapps.weatherapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awesomapps.weatherapp.adapters.ForecastAdapter;
import com.awesomapps.weatherapp.data.JsonResponse;
import com.awesomapps.weatherapp.delegates.AsyncResponse;
import com.awesomapps.weatherapp.delegates.FragmentMessage;
import com.awesomapps.weatherapp.networking.ForecastAPIAsyncTask;
import com.awesomapps.weatherapp.networking.AppConstants;
import com.google.gson.Gson;

import weatherapp.awesomeapps.com.weatherapp.R;

/**
 * Created by Mayank on 8/1/2017.
 */
public class HourlyFragment extends Fragment implements AsyncResponse, FragmentMessage {
    protected String TAG = this.getClass().getSimpleName();
    double longitude;
    double latitude;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hourly_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        getHourlyWeatherReport();
        return view;
    }

    @Override
    public void processFinish(String output) {
        Gson gson = new Gson();
        Log.d(TAG, output);
        JsonResponse jr =  gson.fromJson(output, JsonResponse.class);
        Log.d(TAG, jr.latitude+"");

        ForecastAdapter adapter = new ForecastAdapter(jr.hourly.data, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void sendFragmentMessage(Bundle bundle) {
        longitude = bundle.getDouble(com.awesomapps.weatherapp.Utils.AppConstants.PREFS_LONGITUDE,0);
        latitude = bundle.getDouble(com.awesomapps.weatherapp.Utils.AppConstants.PREFS_LATITUDE,0);
    }

    void getHourlyWeatherReport()
    {
        ForecastAPIAsyncTask forecastAsyncTask = new ForecastAPIAsyncTask();
        forecastAsyncTask.delegate = this;
        forecastAsyncTask.executeTask(getActivity(), AppConstants.FORECAST_API, new double[]{latitude,longitude});
    }
}
