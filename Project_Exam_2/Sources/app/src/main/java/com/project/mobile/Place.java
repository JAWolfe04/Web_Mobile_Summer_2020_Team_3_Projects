package com.project.mobile;

public class Place {
    private String name = "", types = "", id = "";
    private double latitude = 0.0, longitude = 0.0;

    public Place(String id, double latitude, double longitude, String name, String types) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.types = types;
    }

    public String getId() {
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
