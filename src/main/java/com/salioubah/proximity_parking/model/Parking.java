package com.salioubah.proximity_parking.model;

/**
 * Model of Parking
 * We just took 3 fields to represent a parking data
 */
public class Parking {
    private String name;
    private double lat;
    private double lon;

    public Parking(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "{" +
                "name:\"" + name + '\"' +
                ", lat:" + lat +
                ", lon:" + lon +
                '}';
    }
}
