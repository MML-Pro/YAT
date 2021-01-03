package com.test.yatfirstexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {
    private ListView restaurantListView;
    private RestaurantAdapter adapter;
    private final ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://gist.githubusercontent.com/sharkyze/71efb42a5ee58c7a892bd0423f125802/raw/08655067e3bb69dc1c8cde68edc7f901c00b9205/restaurant-menu.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject array = response.getJSONObject("array");
                    JSONObject resturantinfo = array.getJSONObject("restaurant-info");
                    Restaurant restaurant = new Restaurant();
                    restaurant.setRestaurantName(resturantinfo.getString("name"));
                    restaurant.setRestaurantAddress(resturantinfo.getString("address"));
                    restaurant.setRestaurantLogo(resturantinfo.getString("logo"));

                    restaurantArrayList.add(restaurant);
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);


        restaurantListView = findViewById(R.id.restaurantListView);
        adapter = new RestaurantAdapter(this,R.layout.restaurant_row,restaurantArrayList);
        restaurantListView.setAdapter(adapter);

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        restaurantListView = findViewById(R.id.restaurantListView);

        progressBar = findViewById(R.id.progressBar);
        new MyAsyncTask().execute();


    }

    public class MyAsyncTask extends AsyncTask<Void, Void, JSONObject> {

        DataRetreival retreival;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            retreival = new DataRetreival();
            return retreival.getRestaurantJson("https://gist.githubusercontent.com/sharkyze/71efb42a5ee58c7a892bd0423f125802/raw/08655067e3bb69dc1c8cde68edc7f901c00b9205/restaurant-menu.json");
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.setVisibility(View.GONE);
            restaurantArrayList = (retreival.parseJson(jsonObject));
            Log.e("size", restaurantArrayList.size() + "");
            Log.e("ay7aga",restaurantArrayList.get(0).getRestaurantName());
            adapter = new RestaurantAdapter(RestaurantActivity.this, R.layout.restaurant_row, restaurantArrayList);
            restaurantListView.setAdapter(adapter);
        }
    }
}*/