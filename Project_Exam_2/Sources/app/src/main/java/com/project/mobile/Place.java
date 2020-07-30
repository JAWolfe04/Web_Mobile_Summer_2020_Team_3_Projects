package com.project.mobile;

public class Place {
    private String name = "", types = "", id = "", address = "";

    public Place() {}

    public Place(String id, String name, String types, String address) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypes() {
        return types;
    }

    public String getAddress() { return address; }
}
