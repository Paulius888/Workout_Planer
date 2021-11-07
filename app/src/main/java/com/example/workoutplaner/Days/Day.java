package com.example.workoutplaner.Days;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Day implements Serializable {

    @Exclude
    private String key;
    private String name;
    private String userID;
    private String workoutID;

    public Day(){}
    public Day(String name, String userID, String workoutID)
    {
        this.name = name;
        this.userID = userID;
        this.workoutID = workoutID;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getWorkoutID() { return workoutID; }

    public String getKey(){
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(String userID) { this.userID = userID; }

    public void setWorkoutID(String workoutID) { this.workoutID = workoutID; }

    public void setKey(String key)
    {
        this.key = key;
    }
}
