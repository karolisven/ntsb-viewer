package com.isa.ntsb.dto;

/**
 * Created by Karolis on 7/3/2019.
 */
public class EventDataForEngine {

    String lat;
    String lon;
    String description;

    public EventDataForEngine(String lat, String lon, String description) {
        this.lat = lat;
        this.lon = lon;
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
