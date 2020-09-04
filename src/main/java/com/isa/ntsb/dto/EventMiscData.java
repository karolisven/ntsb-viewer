package com.isa.ntsb.dto;

/**
 * Created by Karolis on 7/2/2019.
 */
public class EventMiscData {

    String lat;
    String lon;
    int findingNo;
    String description;

    public EventMiscData(String lat, String lon, int findingNo, String description) {
        this.lat = lat;
        this.lon = lon;
        this.findingNo = findingNo;
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
