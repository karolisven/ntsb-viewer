package com.isa.ntsb.dto;

import java.util.Date;

/**
 * Created by Karolis on 6/27/2019.
 */
public class EventDataByLatLon {

    Date date;
    String lat;
    String lon;
    String description;
    String state;

    public EventDataByLatLon(Date date, String lat, String lon, String description, String state) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.description = description;
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
