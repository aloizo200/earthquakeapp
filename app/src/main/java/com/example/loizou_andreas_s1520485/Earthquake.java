package com.example.loizou_andreas_s1520485;

import android.support.annotation.NonNull;

import java.util.Date;

class Earthquake implements Comparable{
    private String title;
    private String description;
    private Date pubDate;
    private String category;
    private double geoLat;
    private double geoLong;
    private int depth;
    private double magnitute;

    public Earthquake (){
        indicator = "false";

    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    private String indicator;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getMagnitute() {
        return magnitute;
    }

    public void setMagnitute(double magnitute) {
        this.magnitute = magnitute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
        String [] tokens = description.split(";");
        String depth = tokens[3];
        depth = depth.replace(" ", "");
        depth = depth.replace("km","" );
        this.depth = Integer.parseInt(depth.replace("Depth:", ""));

        String magnitute = tokens[4];
        magnitute = magnitute.replace(" ", "");
        this.magnitute = Double.parseDouble(magnitute.replace("Magnitude:", ""));

    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public double getGeoLat()
    {
        return geoLat;
    }

    public void setGeoLat(double geoLat)
    {
        this.geoLat = geoLat;
    }

    public double getGeoLong()
    {
        return geoLong;
    }

    public void setGeoLong(double geoLong)
    {
        this.geoLong = geoLong;
    }


@Override
    public String toString(){
        return description;
}
@Override
    public int compareTo(@NonNull Object o)
{


        if (o instanceof Earthquake)
        {
            Earthquake e = (Earthquake) o;
           if(indicator.equals("magn"))
           {
            Double localMagn = Double.parseDouble(String.valueOf(magnitute));
            Double var = Double.parseDouble(String.valueOf(e.getMagnitute()));
            return localMagn.compareTo(var);
           }
           else if(indicator.equals("depth"))
           {
               Integer localDepth = Integer.parseInt(String.valueOf(depth));
               Integer var = Integer.parseInt(String.valueOf(e.getDepth()));
               return localDepth.compareTo(var);
           }else if(indicator.equals("lat")){
               Double locallat = Double.parseDouble(String.valueOf(geoLat));
               Double var = Double.parseDouble(String.valueOf(e.getGeoLat()));
               return locallat.compareTo(var);

           }else if(indicator.equals("long")){
               Double locallong = Double.parseDouble(String.valueOf(geoLong));
               Double var = Double.parseDouble(String.valueOf(e.getGeoLong()));
               return locallong.compareTo(var);

           }


        }
        return 0;
}



}

