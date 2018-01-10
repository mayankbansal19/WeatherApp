package com.awesomapps.weatherapp.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Mayank on 7/5/2017.
 */
public class LocationGetter {

    public Location getLocation(Context context)
    {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        try
        {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (SecurityException ex)
        {ex.printStackTrace();}

        if(location != null) {
//            showMyAddress(location);
        }

        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
//                showMyAddress(location);
            }

            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }
        };
        try
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        catch (SecurityException ex)
        {
            ex.printStackTrace();
        }
        return location;
    }
}
