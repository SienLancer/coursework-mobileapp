package com.example.courework.models;

public class Hiker {
    private String id;
    private String name;
    private String location;
    private String doh;
    private String parking;
    private int length;
    private String level;
    private String description;

    public Hiker(String id, String name, String location, String doh, String parking, int length, String level, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.doh = doh;
        this.parking = parking;
        this.length = length;
        this.level = level;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDoh() {
        return doh;
    }

    public void setDoh(String doh) {
        this.doh = doh;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
