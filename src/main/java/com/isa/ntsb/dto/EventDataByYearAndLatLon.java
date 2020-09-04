package com.isa.ntsb.dto;

import java.util.Date;

/**
 * Created by Karolis on 7/1/2019.
 */
public class EventDataByYearAndLatLon {

    Date date;
    String city;
    String description;
    String lat;
    String longitude;

    public EventDataByYearAndLatLon(Date date, String city, String description, String lat, String longitude) {
        this.date = date;
        this.city = city;
        this.description = description;
        this.lat = lat;
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
