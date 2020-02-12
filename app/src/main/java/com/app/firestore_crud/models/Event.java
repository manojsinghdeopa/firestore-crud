package com.app.firestore_crud.models;

import java.util.Random;

/**
 * Created by Manoj Singh on 12-02-2020.
 * immanojsingh007@gmail.com
 */
public class Event {
    private String id = String.valueOf(new Random().nextInt());
    private String name;
    private String place;
    private String type;
    private String startTime;
    private String endTime;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id=id;
    }
}
