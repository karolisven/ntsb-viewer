package com.isa.ntsb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Karolis on 6/20/2019.
 */
@Entity
public class State {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String abbreviation;
    private String name;

    public State(){}

    public State(String abbreviation, String name){
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
