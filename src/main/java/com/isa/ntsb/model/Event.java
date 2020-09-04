package com.isa.ntsb.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String accNo;
    private String eventId;
    private Date date;
    private String city;

    @ManyToOne
    private State state;
    private int year;
    private String latitude;
    private String longitude;
    private Date lchgdate;

    @OneToMany(mappedBy = "event")
    List<Finding> findings;

    @OneToMany(mappedBy = "event")
    List<Occurrence> occurrences;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<Finding> getFindings() {
        return findings;
    }

    public void setFindings(List<Finding> findings) {
        this.findings = findings;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Date getLchgdate() {
        return lchgdate;
    }

    public void setLchgdate(Date lchgdate) {
        this.lchgdate = lchgdate;
    }

    public String toString() {
        return  "ev_state: " + getState() + " | ev_year: " + getYear();
    }

}