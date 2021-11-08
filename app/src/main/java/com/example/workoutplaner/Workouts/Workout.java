package com.example.workoutplaner.Workouts;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Workout implements Serializable {

    @Exclude
    private String key;
    private String name;
    private String user;
    public Workout(){}
    public Workout(String name, String user)
    {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
