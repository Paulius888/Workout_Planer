package com.example.workoutplaner.Exercises;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int sets;
    private String userID;
    private String dayID;
    private String key;

    public Exercise(){

    }

    public Exercise(String name, int sets, String userid, String dayid){
        this.name = name;
        this.sets = sets;
        this.userID = userid;
        this.dayID = dayid;
    }

    public String getName(){
        return name;
    }

    public int getSets(){
        return sets;
    }

    public String getKey(){
        return this.key;
    }

    public String getDayID() { return dayID; }

    public String getUserID(){
        return userID;
    }

    public void setKey(String key){
        this.key = key;
    }
}
