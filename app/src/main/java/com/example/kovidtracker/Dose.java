package com.example.kovidtracker;

public class Dose {

    private String brand;
    private String batch;
    private String facility;
    private String date;

    public Dose(){

    }

    public Dose(String brand, String batch, String facility, String date) {
        this.brand = brand;
        this.batch = batch;
        this.facility = facility;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }
}
