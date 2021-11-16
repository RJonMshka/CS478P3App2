package com.example.project3app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PlaceReceiver extends BroadcastReceiver {
    private static final String ATTRACTIONS = "edu.uic.cs478.fall2021.project3.attractions";
    private static final String RESTAURANTS = "edu.uic.cs478.fall2021.project3.restaurants";

    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getStringExtra("placeType");

        // Check if broadcasted intent was for attractions or for restaurants
        if(s.equals(ATTRACTIONS)) {
            Intent activityIntent = new Intent(context, AttractionActivity.class);
            context.startActivity(activityIntent);
        } else if(s.equals(RESTAURANTS)) {
            Intent activityIntent = new Intent(context, RestaurantActivity.class);
            context.startActivity(activityIntent);
        }
    }
}