package com.example.loizou_andreas_s1520485;

import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RadioButtonListener implements RadioGroup.OnCheckedChangeListener {
    private MainActivity ma;
    public RadioButtonListener(MainActivity ma) {
        this.ma = ma;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.deepest)
    {

        List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"depth");
        Collections.sort(le);
        Collections.reverse(le);
        ma.paintView(le);
    }else if (checkedId == R.id.largestM)
    {

        List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"magn");
        Collections.sort(le);
        Collections.reverse(le);
        ma.paintView(le);

    }else if(checkedId == R.id.Shallow) {

            List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"depth");
            Collections.sort(le);
            ma.paintView(le);

        }else if(checkedId == R.id.mostN){
            List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"lat");
            Collections.sort(le);
            Collections.reverse(le);
            ma.paintView(le);

        }else if(checkedId == R.id.mostS){
            List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"lat");
            Collections.sort(le);
            ma.paintView(le);
        }else if(checkedId == R.id.mostE){
            List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"long");
            Collections.sort(le);
            Collections.reverse(le);
            ma.paintView(le);

        }else if(checkedId == R.id.mostW){
            List<Earthquake> le = liste(new ArrayList<Earthquake>(ma.getEarthquake()),"long");
            Collections.sort(le);
            ma.paintView(le);

        }

    }



 public List<Earthquake> liste (List<Earthquake> listearth, String parameter){


        for (Earthquake e: listearth){
            e.setIndicator(parameter);

        }
        return listearth;
    }
    }

