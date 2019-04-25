package com.example.loizou_andreas_s1520485;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateSearchListener implements View.OnClickListener {
    private MainActivity ma;
    public DateSearchListener(MainActivity ma) {
        this.ma = ma;
    }

    @Override
    public void onClick(View v) {
        EditText s = (EditText) ma.findViewById(R.id.searchView5);
        String startingDate = s.getText().toString();
        EditText s1 = (EditText) ma.findViewById(R.id.searchView4);
        String endDate = s1.getText().toString();
        ArrayList<Earthquake> Le = new ArrayList<>();
        if (endDate.equals("") || endDate.equals("DD/MM/YYYY")){
            if (isDate(startingDate)) {
            for (Earthquake e : ma.getEarthquake()) {

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date pdate = null;
                    try {
                        pdate = formatter.parse(startingDate);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    Calendar calendar = Calendar.getInstance();
                    Calendar calendar1 = Calendar.getInstance();
                    calendar.setTime(pdate);
                    calendar1.setTime(e.getPubDate());
                    if (pdate != null && calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
                            && calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)
                            && calendar.get(Calendar.MONTH) == calendar1.get(Calendar.MONTH)) {
                        Le.add(e);

                    }
                }
                ma.paintView(Le);
            }else{
                ma.popAlert();
            }
        }else {
            if(isDate(startingDate)&&isDate(endDate)){
                for (Earthquake e : ma.getEarthquake()) {

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date startdate = null;
                    Date end = null;
                    try {
                        startdate = formatter.parse(startingDate);
                        end = formatter.parse(endDate);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }


                    if (startdate != null && end!=null&& e.getPubDate().after(startdate) && e.getPubDate().before(end)) {
                        Le.add(e);

                    }
                }
                ma.paintView(Le);
            }else {
                ma.popAlert();
            }
        }
    }

    public boolean isDate(String date){
        if(date.contains("/")) {
            String[] tokens = date.split("/");
            if(tokens.length==3){
                for(String s : tokens){
                    try {
                       Integer.parseInt(s);
                    }catch (NumberFormatException e){
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }
}



