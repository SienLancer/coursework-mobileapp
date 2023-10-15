package com.example.courework.models;

import android.net.Uri;

public class Observation {
    private String id;
    private String name;
    private String timeOfOb;
    private String comment;
    private String hiker_id;


    public Observation(String id, String name, String timeOfOb, String comment, String hiker_id) {
        this.id = id;
        this.name = name;
        this.timeOfOb = timeOfOb;
        this.comment = comment;
        this.hiker_id = hiker_id;
    }

    public String getTimeOfOb() {
        return timeOfOb;
    }

    public void setTimeOfOb(String timeOfOb) {
        this.timeOfOb = timeOfOb;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHiker_id() {
        return hiker_id;
    }

    public void setHiker_id(String hiker_id) {
        this.hiker_id = hiker_id;
    }
}
