package com.codebrewers.round1;

public class AQIModel {
    String lat,lon,aqi,co,methane_butane;

    public AQIModel(String lat, String lon, String aqi, String co, String methane_butane) {
        this.lat = lat;
        this.lon = lon;
        this.aqi = aqi;
        this.co = co;
        this.methane_butane = methane_butane;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }



    public String getAqi() {
        return aqi;
    }

    public String getCo() {
        return co;
    }

    public String getMethane_butane() {
        return methane_butane;
    }
}
