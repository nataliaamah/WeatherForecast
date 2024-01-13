package com.example.project141.Domains;

public class FutureDomain {
    private String location;
    private int highTemp;
    private int lowTemp;

    public FutureDomain(String location, int highTemp, int lowTemp) {
        this.location = location;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
    }

    public String getLocation() {
        return location;
    }

    public void setDay(String day) {
        this.location = day;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }
}
