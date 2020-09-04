package com.isa.ntsb.dto;

import java.util.Date;

/**
 * Created by Karolis on 6/27/2019.
 */
public class EventDataByClickedState {

    Date date;
    String city;
    String lat;
    String lon;
    int findingNo;
    String description;

    public EventDataByClickedState(Date date, String city, String lat, String lon, int findingNo, String description) {
        this.date = date;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.findingNo = findingNo;
        this.description = description;
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

    public int getFindingNo() {
        return findingNo;
    }

    public void setFindingNo(int findingNo) {
        this.findingNo = findingNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
