package org.example.manager.Models;

public class LogisticsNetwork {

    private String country1;
    private String country2;
    private int distance;

    public LogisticsNetwork() {}

    public LogisticsNetwork(String country1, String country2, int distance) {
        this.country1 = country1;
        this.country2 = country2;
        this.distance = distance;
    }

    public String getCountry1() {
        return country1;
    }

    public String getCountry2() {
        return country2;
    }

    public int getDistance() {
        return distance;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
