
package com.intedemoapp.controller;

import android.util.Log;

import com.intedemoapp.model.SampleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class for Parsing JSON data and providing data in list form.
 *
 *  Created by shobharam.piplode on 3/10/2016.
 */
public class DataProvider
{
    private static final String TAG = "DataProvider";

    private static String TAG_ID = "id";
    private static String TAG_NAME = "name";
    private static String TAG_FROMCENTRAL = "fromcentral";
    private static String TAG_CAR = "car";
    private static String TAG_TRAIN = "train";
    private static String TAG_LOCATION = "location";
    private static String TAG_LATITUDE = "latitude";
    private static String TAG_LONGITUDE = "longitude";

    private static List<SampleData> dataList;

    protected JSONArray parseUrl(String urlString) {
        InputStream is = null;
        try {
            java.net.URL url = new java.net.URL(urlString);
            URLConnection urlConnection = url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            return new JSONArray(json);
        } catch (Exception e) {
            Log.d(TAG, "Failed to parse the json for media list", e);
            return null;
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static List<SampleData> buildData(String url) throws JSONException {

        dataList = new ArrayList<>();

        JSONArray userList = new DataProvider().parseUrl(url);

        if (null != userList)
        {
            for (int i = 0; i < userList.length(); i++)
            {
                SampleData sampleData = new SampleData();
                SampleData.Fromcentral fromcentral = sampleData.new Fromcentral();
                SampleData.Location location = sampleData.new Location();

                JSONObject user = userList.getJSONObject(i);
                if(user!=null)
                {
                    int id = user.getInt(TAG_ID);
                    String name = user.getString(TAG_NAME);

                    JSONObject fromcentralJObject = user.getJSONObject(TAG_FROMCENTRAL);

                    String car = fromcentralJObject.getString(TAG_CAR);
                    fromcentral.setCar(car);

                    String train = null;
                    try {
                        train = fromcentralJObject.getString(TAG_TRAIN);
                        fromcentral.setTrain(train);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject locationJObject = user.getJSONObject(TAG_LOCATION);
                    double  latitude = locationJObject.getDouble(TAG_LATITUDE);
                    double  longitude = locationJObject.getDouble(TAG_LONGITUDE);
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);

                    sampleData.setId(id);
                    sampleData.setName(name);
                    sampleData.setFromcentral(fromcentral);
                    sampleData.setLocation(location);

                    dataList.add(sampleData);
                }
            }
        }
        return dataList;
    }
}
