package com.test.yatfirstexercise;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by NH on 20-Jan-18.
 */
public class DataRetreival {

    public JSONObject getRestaurantJson(String urlStr) {
        JSONObject dataArray = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                is.close();
                connection.disconnect();
                String dataStr = stringBuilder.toString();
                Log.e("data", dataStr);
                dataArray = new JSONObject(dataStr);
            }
        } catch (MalformedURLException e) {
            Log.e("url exception", "not valid url");
        } catch (IOException e) {
            Log.e("connection error", "can not connect to this url");
        } catch (JSONException e) {
            Log.e("jsonArray error", "can not convert to json array");
        }
        return dataArray;
    }

    public ArrayList<Restaurant> parseJson(JSONObject jsonObject) {
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();

            try {
                Restaurant restaurant = new Restaurant();

                JSONObject array = jsonObject.getJSONObject("array");
                JSONObject resturantinfo = array.getJSONObject("restaurant-info");

                restaurant.setRestaurantID(resturantinfo.getInt("id"));
                restaurant.setRestaurantName(resturantinfo.getString("name"));
                restaurant.setRestaurantAddress(resturantinfo.getString("address"));
                restaurant.setRestaurantLogo(resturantinfo.getString("logo"));

                restaurantArrayList.add(restaurant);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Log.e("size", restaurantArrayList.size() + "");
        return restaurantArrayList;
    }
}
