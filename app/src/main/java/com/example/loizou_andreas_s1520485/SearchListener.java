package com.example.loizou_andreas_s1520485;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchListener implements  View.OnClickListener {
    private MainActivity ma;
    private String parameter;
    public SearchListener(MainActivity ma) {
        this.ma = ma;


    }


    @Override
    public void onClick(View v) {
        List<Earthquake> Le = new ArrayList<>();
        System.out.println(parameter);
        EditText searchv = ma.findViewById(R.id.searchView);
        parameter = searchv.getText().toString();
        if (parameter.equals("")){
            ma.paintView(ma.getEarthquake());

        }else {
            for (Earthquake e : ma.getEarthquake()) {
                if (e.getDescription().toLowerCase().contains(parameter)) {
                    Le.add(e);

                }
            }
            ma.paintView(Le);
        }

    }
}
