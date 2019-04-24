package com.example.loizou_andreas_s1520485;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchListener implements View.OnKeyListener {
    private MainActivity ma;
    private String parameter;
    public SearchListener(MainActivity ma) {
        this.ma = ma;


    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText s = (EditText) ma.findViewById(R.id.searchView);
        this.parameter = s.getText().toString();
        //System.out.println(parameter);
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            //System.out.println(parameter);
            if(keyCode == event.KEYCODE_ENTER){
                List<Earthquake> Le = new ArrayList<>();
                System.out.println(parameter);
                if (parameter.equals("")){
                    ma.paintView(ma.getEarthquake());
                }else {
                    for (Earthquake e : ma.getEarthquake()) {
                        if (e.getDescription().toLowerCase().contains(parameter)) {
                            Le.add(e);

                        }
                    }
                    System.out.println(Le.toString());
                    ma.paintView(Le);
                }

            }
        }
        return false;

    }
}
