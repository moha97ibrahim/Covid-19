package com.mohammedibrahim.covid_19.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CovidData {
    @SerializedName("activeCases")
    private int activeCases;
    @SerializedName("recovered")
    private int recovered;
    @SerializedName("deaths")
    private int deaths;
    @SerializedName("totalCases")
    private int totalCases;
    @SerializedName("regionData")
    private List<byState> regionData = new ArrayList<>();
    @SerializedName("lastUpdatedAtApify")
    private String lastUpdatedAtApify;


    public class byState {
        @SerializedName("region")
        private String region;
        @SerializedName("totalInfected")
        private int totalInfected;
        @SerializedName("recovered")
        private int recovered;
        @SerializedName("deceased")
        private int deceased;
        @SerializedName("totalCases")
        private int totalCases;


        public byState(String region, int totalInfected, int recovered, int deceased, int totalCases) {
            this.region = region;
            this.totalInfected = totalInfected;
            this.recovered = recovered;
            this.deceased = deceased;
            this.totalCases = totalCases;

        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getTotalInfected() {
            return totalInfected;
        }

        public void setTotalInfected(int totalInfected) {
            this.totalInfected = totalInfected;
        }

        public int getRecovered() {
            return recovered;
        }

        public void setRecovered(int recovered) {
            this.recovered = recovered;
        }

        public int getDeceased() {
            return deceased;
        }

        public void setDeceased(int deceased) {
            this.deceased = deceased;
        }

        public int getTotalCases() {
            return totalCases;
        }

        public void setTotalCases(int totalCases) {
            this.totalCases = totalCases;
        }

    }

    public CovidData(int activeCases, int recovered, int deaths, int totalCases, String lastUpdatedAtApify) {
        this.activeCases = activeCases;
        this.recovered = recovered;
        this.deaths = deaths;
        this.totalCases = totalCases;
        this.lastUpdatedAtApify = lastUpdatedAtApify;
    }


    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
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

    public List<byState> getRegionData() {
        return regionData;
    }

    public void setRegionData(List<byState> regionData) {
        this.regionData = regionData;
    }

    public String getLastUpdatedAtApify() {
        return lastUpdatedAtApify;
    }

    public void setLastUpdatedAtApify(String lastUpdatedAtApify) {
        this.lastUpdatedAtApify = lastUpdatedAtApify;
    }
}
