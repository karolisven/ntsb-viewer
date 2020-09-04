package com.isa.ntsb.dto;

import java.util.Date;

/**
 * Created by Karolis on 6/27/2019.
 */
public class EventDataByStateAndYear {

    Date date;
    String city;
    String accNo;
    String lat;
    String lon;

    public EventDataByStateAndYear(Date date, String city, String accNo, String lat, String lon) {
        this.date = date;
        this.city = city;
        this.accNo = accNo;
        this.lat = lat;
        this.lon = lon;
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

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
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
}
