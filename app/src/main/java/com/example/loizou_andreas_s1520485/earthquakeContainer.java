package com.example.loizou_andreas_s1520485;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class earthquakeContainer extends LinearLayout {
    private MainActivity ma;
    private double geoLong;
    private double geoLat;
    private String description;
    private String geoLongLat;
    public earthquakeContainer(Context context, String title, String description, double geoLong, double geoLat, double howStrong, MainActivity ma) {
        super(context);
        inflate(getContext(),R.layout.earthquake_container, this);
this.ma = ma;
this.geoLat = geoLat;
this.geoLong = geoLong;
this.description = description;
this.geoLongLat = geoLong+"/"+geoLat;
        TextView titleView = (TextView) findViewById(R.id.Title);
        titleView.setText(title);



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

    public void setButtonListener() {
        Button mapButton = findViewById(R.id.ViewMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query="+geoLat+","+geoLong+""));
                ma.startActivity(intent);
            }
        });
    }

    public void setExpandListener(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Description: "+description+"\n \n \n Long\\Lat: "+geoLongLat);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


        });
    }
}
