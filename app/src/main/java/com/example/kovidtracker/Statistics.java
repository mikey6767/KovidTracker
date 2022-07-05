package com.example.kovidtracker;
// POJO - Java Class

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("todayCases")
    @Expose
    private String todayCases;

    @SerializedName("critical")
    @Expose
    private String critical;

    @SerializedName("todayRecovered")
    @Expose
    private String todayRecovered;

    @SerializedName("todayDeaths")
    @Expose
    private String todayDeaths;


    public Statistics(
            String todayCases,
            String todayDeaths,
            String critical,
            String todayRecovered
    ) {
        this.critical = critical;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
        this.todayRecovered = todayRecovered;
    }

    public String getTodayCases() {
        return this.todayCases;
    }

    public String getCritical() {
        return this.critical;
    }

    public String getTodayDeaths() {
        return this.todayDeaths;
    }

    public String getTodayRecovered() {
        return this.todayRecovered;
    }

}