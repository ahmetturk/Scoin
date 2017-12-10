package com.example.ahmet.scoin;

/**
 * Created by ahmet on 10.12.2017.
 */

public class Scoin {
    private long date;
    private double value;

    public Scoin() {
    }

    public long getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
