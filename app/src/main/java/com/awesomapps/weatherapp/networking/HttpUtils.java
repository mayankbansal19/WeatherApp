package com.awesomapps.weatherapp.networking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mayank on 7/5/2017.
 */
public class HttpUtils {

    private String Cache_Control;
    private String Expires;
    private String X_Forecast_API_Calls;
    private String X_Response_Time;

    public static String sendPostRequest(double latitude, double longitude, String api) {
        String tempURL = AppConstants.BASE_URL()+api+"/"+ AppConstants.KEY+"/"+latitude+","+longitude;
        BufferedReader reader = null;
        String s = "";
        String response = "";
      try {
          URL url = new URL(tempURL+"?exclude=daily,minutely&units=si");


          HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
          conn.setUseCaches(false);
          conn.setDoInput(true);
          conn.setDoOutput(false);
          conn.setRequestMethod("GET");
          conn.setReadTimeout(10000);
          conn.setConnectTimeout(15000);
          conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
          Log.d("HTTPUtils","Conn response code "+conn.getResponseCode());
          if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
          {

              //obtain the encoding returned by the server
              String encoding = conn.getContentEncoding();

              try {
                  //create the appropriate stream wrapper based on the encoding type
                  //use UTF-8 when parsing the JSON responses

                  if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                      reader = new BufferedReader(new InputStreamReader( new GZIPInputStream( conn.getInputStream() ), "UTF-8"));
                  } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
                      reader = new BufferedReader(new InputStreamReader( new InflaterInputStream( conn.getInputStream(), new Inflater(true) ), "UTF-8"));
                  } else {
                      reader = new BufferedReader(new InputStreamReader( conn.getInputStream(),"UTF-8" ));
                  }

                  while( (s = reader.readLine()) != null )
                      response = s;
         //         Log.d("HTTPUtils","response: "+response);
              } catch (IOException e){
                  Log.d("HTTPUtils","Error: "+e.getMessage());
              } finally {
                  if (reader != null) {
                      try {
                          reader.close();
                          reader = null;
                      } catch (IOException e) {
                          Log.d("HTTPUtils","Error: "+e.getMessage());
                      }
                  }
              }

          } //if HTTP_OK - End
          // else if HttpURLConnection Not Ok
          else {

              try {
                  reader = new BufferedReader(new InputStreamReader( conn.getErrorStream() ));
                  while( (s = reader.readLine()) != null )
                      response = s;
              } catch (IOException e){
                  Log.d("HTTPUtils","Error: "+e.getMessage());
              } finally {
                  if (reader != null) {
                      try {
                          reader.close();
                          reader = null;
                      } catch (IOException e) {
                          Log.d("HTTPUtils","Error: "+e.getMessage());
                      }
                  }
              }
              //If response is not ok print error and return null
              Log.d("HTTPUtils","Bad Response: " + response + "\n");
              return null;

          }
      }catch(Exception ex)
      {
          Log.d("HTTPUtils", "exception occurred");
          ex.printStackTrace();
      }
        return response;
    }
}


