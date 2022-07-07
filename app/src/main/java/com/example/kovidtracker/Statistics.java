package com.example.kovidtracker;
// POJO - Java Class

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("testedPositive")
    @Expose
    private String testedPositive;

    @SerializedName("deceased")
    @Expose
    private String deceased;

    @SerializedName("recovered")
    @Expose
    private String recovered;

    @SerializedName("critical")
    @Expose
    private String critical;


    public String getTestedPositive() {
        return testedPositive;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getCritical() {
        return this.critical;
    }

    public Statistics(
            String testedPositive,
            String deceased,
            String critical,
            String recovered
    ) {
        this.critical = critical;
        this.testedPositive = testedPositive;
        this.deceased = deceased;
        this.recovered = recovered;
    }





}