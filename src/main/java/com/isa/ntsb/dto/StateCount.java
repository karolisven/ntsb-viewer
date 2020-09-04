package com.isa.ntsb.dto;

/**
 * Created by Karolis on 6/25/2019.
 */
public class StateCount {

    private String name;
    private long count;

    public StateCount() {}

    public StateCount(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StateCount{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
