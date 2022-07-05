package com.example.kovidtracker;

public class History {

    private String location;
    private String date;

    public History(){

    }

    public History(String location, String date) {
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
