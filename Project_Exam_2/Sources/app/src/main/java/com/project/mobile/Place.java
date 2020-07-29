package com.project.mobile;

public class Place {
    private int id = 0;
    private String name = "", types = "";
    private double latitude = 0.0, longitude = 0.0;

    public Place(int id, double latitude, double longitude, String name, String types) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getTypes() {
        return types;
    }
}
