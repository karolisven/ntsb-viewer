package com.isa.ntsb.model;

import javax.persistence.*;

/**
 * Created by Karolis on 6/25/2019.
 */

@Entity
public class Finding {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Event event;
    private String description;
    private int number;

    public Finding(){}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
