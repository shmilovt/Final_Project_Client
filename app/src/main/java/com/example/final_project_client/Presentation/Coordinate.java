package com.example.final_project_client.Presentation;

/**
 * Created by TAMIR on 5/1/2018.
 */

public class Coordinate {
    private double lat;
    private double lon;

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
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
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Coordinate)) return false;
        Coordinate otherCoordinate = (Coordinate) other;
        if (otherCoordinate.getLat() == lat && otherCoordinate.getLon() == lon)
            return true;
        return false;
    }
}
