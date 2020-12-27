package com.test.yatfirstexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {
    private ListView restaurantListView;
    private RestaurantAdapter adapter;
    private ArrayList<Restaurant> restaurantArrayList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        restaurantListView = findViewById(R.id.restaurantListView);

        progressBar = findViewById(R.id.progressBar);
        new MyAsyncTask().execute();


    }

    public class MyAsyncTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            DataRetreival retreival = new DataRetreival();
            return retreival.getRestaurantJson("https://gist.githubusercontent.com/sharkyze/71efb42a5ee58c7a892bd0423f125802/raw/08655067e3bb69dc1c8cde68edc7f901c00b9205/restaurant-menu.json");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            progressBar.setVisibility(View.GONE);
            restaurantArrayList = (new DataRetreival()).parseJson(jsonArray);
            Log.e("size", restaurantArrayList.size() + "");
            adapter.notifyDataSetChanged();
            adapter = new RestaurantAdapter(RestaurantActivity.this, R.layout.restaurant_row, restaurantArrayList);
            restaurantListView.setAdapter(adapter);
        }
    }
}