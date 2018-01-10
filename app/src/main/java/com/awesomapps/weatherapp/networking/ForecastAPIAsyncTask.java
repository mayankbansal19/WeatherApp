package com.awesomapps.weatherapp.networking;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.awesomapps.weatherapp.Utils.FileIO;
import com.awesomapps.weatherapp.Utils.LocationGetter;
import com.awesomapps.weatherapp.delegates.AsyncResponse;
import com.awesomeapps.weatherapp.main.MainActivity;

/**
 * Created by Mayank on 7/16/2017.
 */
public class ForecastAPIAsyncTask extends AsyncTask<String, Void, String>{
    final String TAG = this.getClass().getSimpleName();
    Context mContext;
    String mApi;
    double[] mLocation;
    public AsyncResponse delegate;

    public void executeTask(Context context, String api, double[] location)
    {
        mContext = context;
        mApi = api;
        mLocation = location;
        this.execute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result =(HttpUtils.sendPostRequest(mLocation[0], mLocation[1],mApi));
        FileIO.generateNoteOnSD(mContext,"/forecastData.txt",result);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        //Log.d(TAG, "Response "+s);
        super.onPostExecute(s);
        delegate.processFinish(s);
    }

}
