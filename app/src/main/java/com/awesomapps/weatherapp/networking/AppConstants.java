package com.awesomapps.weatherapp.networking;

/**
 * Created by Mayank on 7/16/2017.
 */
public class AppConstants {

    private static String base_url;
    public static boolean isProductionEnv;
    public static final String KEY= "87c21a6aba3f535477175c6caab917a1";

    public static final String FORECAST_API = "forecast";

    //region PREFS CONSTANT
    public static final String PREFS_ENV = "env";
    //endregion

    public static String BASE_URL()
    {
        return "https://api.darksky.net/";
    }
}
