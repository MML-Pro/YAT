package com.test.yatfirstexercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private ArrayList<Restaurant> restaurantList;
    private Context context;
    public RestaurantAdapter(Context context, int resource, ArrayList<Restaurant> restaurantList) {
        super(context, resource, restaurantList);
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.restaurant_row,parent,false);

        TextView restaurantName = (TextView) view.findViewById(R.id.restaurant_name);
        TextView restaurantAddress = (TextView) view.findViewById(R.id.restaurant_address);
        ImageView restaurantLogo = (ImageView) view.findViewById(R.id.restaurant_logo);

        restaurantName.setText(restaurantList.get(position).getRestaurantName());
        restaurantAddress.setText(restaurantList.get(position).getRestaurantAddress());


        return view;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }
}
