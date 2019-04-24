package com.example.loizou_andreas_s1520485;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class earthquakeContainer extends LinearLayout {
    public earthquakeContainer(Context context, String title, String description, double geoLong, double geoLat, double howStrong) {
        super(context);
        inflate(getContext(),R.layout.earthquake_container, this);

        TextView titleView = (TextView) findViewById(R.id.Title);
        titleView.setText(title);

        TextView descriptionView = (TextView) findViewById(R.id.Description);
        descriptionView.setText(description);

        TextView geoLatLongView = (TextView) findViewById(R.id.GeoLatLong);
        geoLatLongView.setText(String.valueOf(geoLat) + ", " + String.valueOf(geoLong));

        TextView howStrongView = (TextView) findViewById(R.id.power);
        //If the magnitute of the earthquake is 2.5 or less then the color code will be set to green and and the text to minor
        if (howStrong<= 2.5){
            howStrongView.setText("Minor");
            howStrongView.setBackgroundColor(Color.parseColor("#00FF0E"));
            howStrongView.invalidate();


        }else if (howStrong > 2.5 && howStrong <= 5.4) {
            howStrongView.setText("Light");
            howStrongView.setBackgroundColor(Color.parseColor("#0A9712"));
            howStrongView.invalidate();
        }else if (howStrong > 5.4 && howStrong <= 6.0) {
            howStrongView.setText("Moderate");
            howStrongView.setBackgroundColor(Color.parseColor("#FF8300"));
            howStrongView.invalidate();
        }else if (howStrong > 6.0 && howStrong <=6.9) {
            howStrongView.setText("Strong");
            howStrongView.setBackgroundColor(Color.parseColor("#FE6000"));
            howStrongView.invalidate();
        }else if (howStrong > 6.9 && howStrong <= 7.9) {
            howStrongView.setText("Major");
            howStrongView.setBackgroundColor(Color.parseColor("#FE3200"));
            howStrongView.invalidate();
        }else {
            howStrongView.setText("Great");
            howStrongView.setBackgroundColor(Color.parseColor("#FE0000"));
            howStrongView.invalidate();
        }


    }

}
