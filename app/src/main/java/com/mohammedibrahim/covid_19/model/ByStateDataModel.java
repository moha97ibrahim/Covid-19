package com.mohammedibrahim.covid_19.model;

public class ByStateDataModel {
    private String region;
    private int infected;
    private int recovered,deaths,totalCases;

    public ByStateDataModel(String region, int infected, int recovered, int deaths, int totalCases) {
        this.region = region;
        this.infected = infected;
        this.recovered = recovered;
        this.deaths = deaths;
        this.totalCases = totalCases;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getInfected() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }
}
